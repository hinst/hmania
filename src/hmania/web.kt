package hmania

import com.sun.net.httpserver.*
import java.util.HashMap
import java.nio.charset.StandardCharsets

val ContentTypeHttpHeaderKey = "Content-Type"
val ContentTypePlainTextUTF8 = "text/plain; charset=utf-8"
/** Java default internal charset is UTF-16 */
val ContentTypePlainTextUTF16 = "text/plain; charset=utf-16"
val PageLineEnding = "\r\n"
val actionURLArgumentKey = "action"

fun HttpExchange.respond(data: ByteArray) {
	this.sendResponseHeaders(200, data.count().toLong())
	val stream = this.getResponseBody()!!
	stream.write(data)
	stream.close()
}

fun HttpExchange.respond(text: String) {
	this.getResponseHeaders()!!.set(ContentTypeHttpHeaderKey, ContentTypePlainTextUTF8)
	this.respond(text.getBytesUTF8())
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

fun HttpExchange.getArgumentsAsDebugText(): String {
	val stringBuilder = StringBuilder()
	val arguments = this.getArguments()
	stringBuilder.append("Count of URL arguments: ${arguments.count()}${PageLineEnding}")
	for (argument in arguments) {
		stringBuilder.append("'${argument.key}' = '${argument.value}'${PageLineEnding}")
	}
	return stringBuilder.toString()
}

fun HttpExchange.getRequestHeadersAsDebugText(): String {
	val stringBuilder = StringBuilder()
	val headers = this.getRequestHeaders()!!
	stringBuilder.append("Count of headers: " + headers.size() + PageLineEnding)
	for (header in headers) {
		responseText_sb.append(header.toString())
	}
}

fun String.getBytesUTF8(): ByteArray {
	return this.getBytes(StandardCharsets.UTF_8)
}

fun String.getBytesUTF16(): ByteArray {
	return this.getBytes(StandardCharsets.UTF_16BE)
}
