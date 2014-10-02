package hmania

import org.json.simple.*
import org.json.simple.parser.*
import java.io.FileReader
import hmania.web.getAsterisks

class DataBaseSettings: LoadableJSON {
	var address: String = ""
	var user: String = ""
	var password: String = ""

	class object {
		val defaultFilePath: String = "db.settings.json";
	}

	override fun loadFromJSON(json: Any) {
		json as JSONObject
		address = json.get("address") as String;
		user = json.get("user") as String;
		password = json.get("password") as String;
	}

	override fun toString(): String {
		return "address: '$address', user: '$user', password: '${getAsterisks(password)}'"
	}

}