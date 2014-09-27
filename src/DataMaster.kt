import java.sql.Connection
import javax.sql.*
import org.h2.jdbcx.JdbcDataSource
import java.io.PrintWriter
import java.util.LinkedList
import java.lang.reflect.Constructor

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

	fun obtainConnection(): Connection? {
		var connection = dataSource.getConnection()
		connection?.setAutoCommit(false)
		return connection
	}

	fun loadTable<T: IDataBaseRow>(a: () -> T, connection: Connection, statementString: String): List<T> {
		val statement = connection.createStatement()!!
		val table = statement.executeQuery(statementString)
		val list = listBuilder<T>()
		while (table.next()) {
			val row: T = a()
			row.loadFromTable(table)
			list.add(row)
		}
		return list.build()
	}

}