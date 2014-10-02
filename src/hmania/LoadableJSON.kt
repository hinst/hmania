package hmania

import org.json.simple.JSONValue
import org.json.simple.JSONObject
import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser
import java.io.FileReader
import java.io.StringReader
import java.io.Reader

trait LoadableJSON {

	/** json should be either JSONObject or JSONArray */
	fun loadFromJSON(json: Any)

	fun loadFromJSONReader(reader: Reader) {
		val parser = JSONParser()
		val json = parser.parse(reader)
		loadFromJSON(json!!)
	}

	fun loadFromJSONFile(filePath: String) {
		val reader = FileReader(filePath)
		loadFromJSONReader(reader)
		reader.close()
	}

	fun loadFromJSONString(string: String) {
		val reader = StringReader(string)
		loadFromJSONReader(reader)
		reader.close()
	}

}