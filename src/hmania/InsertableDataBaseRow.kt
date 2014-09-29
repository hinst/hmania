package hmania

import java.sql.PreparedStatement
import java.sql.Connection

trait InsertableDataBaseRow {

	fun getInsertStatement(connection: Connection, tableName: String): PreparedStatement

}