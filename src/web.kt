import com.sun.net.httpserver.*
import java.util.HashMap

val ContentTypeHttpHeaderKey = "Content-Type"
val ContentTypePlainTextUTF8 = "text/plain; charset=utf-8"
val PageLineEnding = "\r\n"

fun HttpExchange.respond(data: ByteArray) {
	this.sendResponseHeaders(200, data.count().toLong())
	val stream = this.getResponseBody()!!
	stream.write(data)
	stream.close()
}

fun HttpExchange.respond(text: String) {
	this.getResponseHeaders()!!.set(ContentTypeHttpHeaderKey, ContentTypePlainTextUTF8)
	this.respond(text.getBytes())
}

fun getURLQueryArguments(query: String): Map<String, String> {
	val result = HashMap<String, String>()
	val pairs = query.split('&')
	for (pairString in pairs) {
		val pair = pairString.split('=')
		if (pair.count() > 0) {
			val key = pair[0]
			val value =
				if (pair.count() > 1)
					pair[1]
				else
					""
			result.put(key, value)
		}
	}
	return result
}

fun HttpExchange.getArguments(): Map<String, String> {
	val query = this.getRequestURI()?.getQuery()
	if (query != null)
		return getURLQueryArguments(query)
	else
		return HashMap()
}

