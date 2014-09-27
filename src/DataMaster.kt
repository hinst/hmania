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

	fun obtainConnection(): Connection {
		val connection = dataSource.getConnection()!!
		connection.setAutoCommit(true)
		return connection
	}

	fun loadTable<T: LoadableDataBaseRow>(connection: Connection, statementString: String, a: () -> T): List<T> {
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

	fun insertRow(connection: Connection, tableName: String, row: InsertableDataBaseRow) {
		val statementString = row.getInsertStatement(tableName)
		val statement = connection.createStatement()!!
		statement.executeUpdate(statementString)
	}

	fun execute(connection: Connection, statementString: String) {
		val statement = connection.createStatement()!!
		statement.executeUpdate(statementString)
	}

	fun execute(statementString: String) {
		val connection = obtainConnection()
		this.execute(connection, statementString)
		connection.close()
	}

}