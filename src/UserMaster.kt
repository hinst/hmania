import org.json.simple.JSONObject
import java.sql.Connection
import java.util.HashMap

class UserMaster(val dataMaster: DataMaster) {

	val nameLengthLimit = 64;
	val passwordLengthLimit = 64;
	val usersTableName = "users"
	val ensureUsersTableStatement = "CREATE TABLE IF NOT EXISTS ${usersTableName}(name VARCHAR(${nameLengthLimit}), password VARCHAR(${passwordLengthLimit}), access INT, sessionID BIGINT, PRIMARY KEY(name));"
	val loadUsersStatement = "SELECT * from ${usersTableName};"

	// Constructor
	{
		ensureUsersTable()
	}

	fun ensureUsersTable() {
		val connection = dataMaster.obtainConnection()!!
		val statement = connection.createStatement()!!
		statement.executeUpdate(ensureUsersTableStatement)
		connection.close()
	}

	fun loadUsersTable(connection: Connection): Map<String, User> {
		val statement = connection.createStatement()!!
		val result = HashMap<String, User>()
		val table = statement.executeQuery(loadUsersStatement)
		while (table.next()) {
			val user = User(table.getString("name")!!)
			user.password = table.getString("password")!!
			user.access = table.getInt("access")!!
			user.sessionID = table.getLong("sessionID")!!
			result.put(user.name, user)
		}
		return result
	}

	fun ensureAdminUser(admin: User) {
		val connection = dataMaster.obtainConnection()!!
		connection.close()
	}

}

