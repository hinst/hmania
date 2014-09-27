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
		connection.commit()
		connection.close()
	}

	fun loadUserList(connection: Connection): List<User> {
		val createUser = { () -> User() }
		val list = dataMaster.loadTable(createUser, connection, loadUsersStatement)
		return list
	}

	fun loadUserMap(connection: Connection): Map<String, User> {
		val list = loadUserList(connection)
		val map = HashMap<String, User>()
		for (user in list) {
			map.put(user.name, user)
		}
		return map
	}

	fun insertUser(connection: Connection, user: User) {
		val statement = connection.createStatement()!!
		statement.executeUpdate(user.toInsertStatement(usersTableName))
	}

	fun ensureUser(user: User) {
		val connection = dataMaster.obtainConnection()!!
		val users = loadUserMap(connection)
		if (false == users.contains(user.name)) {
			insertUser(connection, user)
		}
		connection.close()
	}

}

