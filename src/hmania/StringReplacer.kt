package hmania

import java.util.HashMap

class StringReplacer {

	class object {
		val templateStart = '$'
		val templateEnd = '$'
	}

	val map: HashMap<String, Any> = HashMap()

	fun add(key: String, value: Any) {
		val lKey = templateStart + key + templateEnd
		map.put(lKey, value)
	}

	fun replace(s: String): String {
		var result = s
		for (pair in map) {
			result = result.replace(pair.key, pair.value.toString())
		}
		return result
	}

}