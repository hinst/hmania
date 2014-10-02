package hmania

import java.util.HashMap

class StringReplacer {

	class object {

		val templateStart = '$'
		val templateEnd = '$'

		fun getTemplateString(s: String): String {
			return templateStart + s + templateEnd
		}

	}

	val map: HashMap<String, Any> = HashMap()

	fun add(key: String, value: Any) {
		val lKey = templateStart + key + templateEnd
		map.put(lKey, value)
	}

	fun replace(s: String): String {
		var result = s
		for (pair in map) {
			val key = pair.key
			val present = result.contains(key);
			if (present) {
				val text = replace(pair.value.toString())
				result = result.replace(key, text)
			}
		}
		return result
	}

}