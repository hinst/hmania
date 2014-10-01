package hmania

import org.json.simple.JSONValue
import org.json.simple.JSONObject
import org.json.simple.JSONArray
import org.json.simple.parser.JSONParser
import java.io.FileReader

trait LoadableJSON {

	/** json should be either JSONObject or JSONArray*/
	fun loadFromJSON(json: Any)

	fun loadFromJSONFile(filePath: String) {
		val parser = JSONParser()
		val reader = FileReader(filePath)
		val json = parser.parse(reader)
		loadFromJSON(json!!)
		reader.close()
	}

}