package hmania

fun mapToDebugText<A, B>(map: Map<A, B>?): String {
	val s = StringBuilder()
	if (map != null) {
		s.append("Map contains ${map.size} items\n")
		for (item in map) {
			s.append("'${item.key}' = '${item.value}'\n")
		}
	}
	else
		s.append("Map = null")
	return s.toString()
}