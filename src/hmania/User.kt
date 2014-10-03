package hmania

import java.sql.ResultSet
import org.json.simple.JSONObject
import java.sql.Connection
import java.sql.PreparedStatement

class User(): LoadableDataBaseRow, InsertableDataBaseRow, LoadableJSON {

	class object {

		fun getAccess(string: String): Access {
			try {
				return Access.valueOf(string)
			} catch (e: Exception) {
				return Access.No
			}
		}

		fun loadSessionIDFromJSON(json: JSONObject): Long {
			val sessionID = json.get("sessionID")
			val result =
				if (sessionID != null)
					sessionID as Long
				else
					0
			return result
		}

	}

	enum class Access() {
		No
		User
		Admin
	}

	var name: String = ""
	var password: String = ""
	var access: Access = Access.No
	var sessionID: Long = 0

	override fun loadFromTable(table: ResultSet) {
		name = table.getString("name")!!
		password = table.getString("password")!!
		access = Access.values()[table.getInt("access")]
		sessionID = table.getLong("sessionID")
	}

	override fun getInsertStatement(connection: Connection, tableName: String): PreparedStatement {
		val statementString = "INSERT INTO $tableName (name, password, access, sessionID) VALUES (?, ?, ?, ?);"
		val statement = connection.prepareStatement(statementString)!!
		statement.setString(1, name)
		statement.setString(2, password)
		statement.setInt(3, access.ordinal())
		statement.setLong(4, sessionID)
		return statement
	}

	override fun loadFromJSON(json: Any) {
		json as JSONObject
		name = json.get("name") as String
		password = json.get("password") as String
		access = getAccess(json.get("access") as String)
		sessionID = loadSessionIDFromJSON(json)
	}

	fun encodePassword() {
		val hash = PasswordHash()
		hash.createFromPassword(password)
		password = hash.toJSONString()
	}

	fun checkPassword(password: String): Boolean {
		val hash = PasswordHash()
		hash.loadFromJSONString(this.password)
		val result = hash.checkPassword(password)
		return result
	}

	fun getUpdateSessionStatement(connection: Connection, tableName: String): PreparedStatement {
		val statement =
			connection.prepareStatement(
				"UPDATE $tableName SET sessionID=? where name=?;"
			)!!
		statement.setLong(1, sessionID)
		statement.setString(2, name)
		return statement
	}

}