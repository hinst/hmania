package hmania

import java.sql.ResultSet

trait LoadableDataBaseRow {

	fun loadFromTable(table: ResultSet)

}