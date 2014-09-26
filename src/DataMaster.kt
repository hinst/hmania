import javax.sql.*
import org.h2.jdbcx.JdbcDataSource
import java.io.PrintWriter

class DataMaster(val dataBaseSettings: DataBaseSettings) {

	fun createDataSource(): DataSource {
		val dataSource = JdbcDataSource()
		dataSource.setURL(dataBaseSettings.address)
		dataSource.setUser(dataBaseSettings.user)
		dataSource.setPassword(dataBaseSettings.password)
		dataSource.setLogWriter(
			PrintWriter(System.out)
		)
		return dataSource
	}

	val dataSource: DataSource = createDataSource()

	fun obtainConnection(): java.sql.Connection? {
		var connection = dataSource.getConnection()
		connection?.setAutoCommit(false)
		return connection
	}

}