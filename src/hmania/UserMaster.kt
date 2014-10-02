package hmania

import org.json.simple.JSONObject
import java.sql.Connection
import java.util.HashMap

class UserMaster(val dataMaster: DataMaster) {
	{
		ensureUsersTable()
	}

	class object {

		val nameLengthLimit = 64;
		val passwordLengthLimit = 64;
		val passwordColumnLengthLimit = 128;
		val usersTableName = "users"
		val ensureUsersTableStatement = "CREATE TABLE IF NOT EXISTS ${usersTableName}(name VARCHAR(${nameLengthLimit}), password VARCHAR(${passwordColumnLengthLimit}), access INT, sessionID BIGINT, PRIMARY KEY(name));"
		val loadUsersStatement = "SELECT * from ${usersTableName};"
		val defaultAdminSettingsFilePath = "admin.settings.json"

	}

	fun ensureUsersTable() {
		dataMaster.execute(ensureUsersTableStatement)
	}

	fun loadUserList(connection: Connection): List<User> {
		val createUser = { () -> User() }
		val list = dataMaster.loadTable(connection, loadUsersStatement, createUser)
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
		dataMaster.insertRow(connection, usersTableName, user)
	}

	fun ensureUser(user: User) {
		val connection = dataMaster.obtainConnection()
		val users = loadUserMap(connection)
		if (false == users.contains(user.name)) {
			Log.emit("Now creating admin user '${user.name}'...")
			insertUser(connection, user)
		}
		connection.close()
	}

	fun logIn(user: User) {
		val connection = dataMaster.obtainConnection()
		val list = loadUserMap(connection)
		val matchingUser = list.get(user.name)
		if (matchingUser != null)
			logIn(user, matchingUser)
	}

	private fun logIn(user: User, record: User) {
		val legit = record.checkPassword(user.password)
		if (legit) {
			user.access = record.access
			user.sessionID = PasswordSalt.generateSessionID()
		}
		else {
			user.access = User.Access.No
			user.sessionID = 0
		}
	}

}

