package hmania

import org.json.simple.JSONObject
import org.json.simple.JSONArray

trait SaveableJSON {

	fun saveToJSON(): Any

	fun toJSONString(): String {
		val json = saveToJSON()
		val result =
			if (json is JSONObject)
				json.toJSONString()!!
			else if (json is JSONArray)
				json.toJSONString()!!
			else
				""
		return result
	}

}