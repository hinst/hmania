import java.sql.ResultSet

trait IDataBaseRow {

	open fun loadFromTable(table: ResultSet) {
	}

	open fun saveToInsertStatement(table: String) {
	}

}