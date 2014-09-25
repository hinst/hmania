import javax.sql.*
import org.h2.jdbcx.JdbcDataSource

class DataMaster(val dataBaseSettings: DataBaseSettings) {

	fun createDataSource(): ConnectionPoolDataSource {
		val dataSource = JdbcDataSource()
		dataSource.setURL(dataBaseSettings.address)
		dataSource.setUser(dataBaseSettings.user)
		dataSource.setPassword(dataBaseSettings.password)
		return dataSource
	}

	val dataSource: ConnectionPoolDataSource = createDataSource()

	fun obtainConnection(): java.sql.Connection? {
		var connection = dataSource.getPooledConnection()
		while (null == connection) {
			connection = dataSource.getPooledConnection()
			Thread.sleep(1000)
		}
		return connection?.getConnection();
	}

}