import java.sql.ResultSet
import org.json.simple.JSONObject

class User(): LoadableDataBaseRow, InsertableDataBaseRow, LoadableJSON {
	enum class Access() {
		No
		User
		Admin
	}

	class object {

		fun getAccess(string: String): Access {
			try {
				return Access.valueOf(string)
			} catch (e: Exception) {
				return Access.No
			}
		}

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

	override fun getInsertStatement(tableName: String): String {
		return "INSERT INTO ${tableName} (name, password, access, sessionID) VALUES (${name}, ${password}, ${access.ordinal()}, ${sessionID});"
	}

	override fun loadFromJSON(json: Any) {
		json as JSONObject
		name = json.get("name") as String
		password = json.get("password") as String
		access = getAccess(json.get("access") as String)
		sessionID = json.get("sessionID") as Long
	}

}