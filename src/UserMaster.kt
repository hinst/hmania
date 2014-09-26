import org.json.simple.JSONObject

class UserMaster(val dataMaster: DataMaster) {

	val nameLengthLimit = 64;
	val passwordLengthLimit = 64;
	val ensureUsersTableStatement = "CREATE TABLE IF NOT EXISTS users(name VARCHAR(${nameLengthLimit}), password VARCHAR(${passwordLengthLimit}), access INT);"

	// Constructor
	{
		ensureUsersTable()
	}

	fun ensureUsersTable() {
		val connection = dataMaster.obtainConnection()!!
		val statement = connection.createStatement()!!
		statement.executeUpdate(ensureUsersTableStatement)
	}

}

