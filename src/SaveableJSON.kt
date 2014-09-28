import org.json.simple.JSONObject
import org.json.simple.JSONArray

trait SaveableJSON {

	fun SaveToJSON(): Any

	fun toJSONString(): String {
		val json = SaveToJSON()
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