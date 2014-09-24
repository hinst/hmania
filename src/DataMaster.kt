import javax.sql.*
import org.h2.jdbcx.JdbcDataSource

class DataMaster(val dataBaseSettings: DataBaseSettings) {
	{
		prepareDB()
	}

	fun createDataSource(): ConnectionPoolDataSource {
		val dataSource = JdbcDataSource()
		dataSource.setURL(dataBaseSettings.address)
		dataSource.setUser(dataBaseSettings.user)
		dataSource.setPassword(dataBaseSettings.password)
		return dataSource
	}

	val dataSource: ConnectionPoolDataSource = createDataSource()

	val ensureUsersTableStatement = "CREATE TABLE IF NOT EXISTS users(name TEXT, password TEXT);"

	fun ensureUsersTable() {
		val connection = dataSource.getPooledConnection()!!.getConnection()!!
		val statement = connection.createStatement()!!
		statement.executeUpdate(ensureUsersTableStatement)
	}

	fun prepareDB() {
		ensureUsersTable()
	}

}