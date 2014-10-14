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

	fun loadUserByName(connection: Connection, name: String): User? {
		if (name.isNotEmpty()) {
			val users = loadUserMap(connection)
			val user = users.get(name)
			return user
		}
		else
			return null
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

	fun updateSession(connection: Connection, user: User) {
		val statement = user.getUpdateSessionStatement(connection, usersTableName)
		statement.executeUpdate()
	}

	/** Takes user.name & user.password -> assigns user.access & user.sessionID */
	fun logIn(connection: Connection, user: User) {
		val recordedUser = loadUserByName(connection, user.name)
		if (recordedUser != null) {
			logIn(user, recordedUser)
			if (user.access != User.Access.No) {
				updateSession(connection, user)
			}
		}
	}

	private fun logIn(user: User, recordedUser: User) {
		user.access = User.Access.No
		val legit = recordedUser.checkPassword(user.password)
		if (legit) {
			user.access = recordedUser.access
			user.sessionID = PasswordSalt.generateSessionID()
		} else {
			user.sessionID = 0
		}
	}

	// Does NOT check if user authorized to log out
	// Proceeds to erase stored session id of specified user immediately
	// Modifies user instance
	fun logOut(connection: Connection, user: User) {
		user.sessionID = 0
		updateSession(connection, user)
	}

	// Takes user.name & user.sessionID, assigns user.access
	fun recognize(connection: Connection, user: User) {
		user.access = User.Access.No
		if (user.sessionID != 0.toLong()) {
			val recordedUser = loadUserByName(connection, user.name)
			if (recordedUser != null)
				if (recordedUser.sessionID == user.sessionID) {
					user.access = recordedUser.access
				}
		}
	}

}

