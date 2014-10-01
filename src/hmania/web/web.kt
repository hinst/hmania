package hmania.web

import com.sun.net.httpserver.*
import java.util.HashMap
import java.nio.charset.StandardCharsets
import java.io.InputStreamReader
import java.io.BufferedReader
import java.net.URLDecoder

val actionURLArgumentKey = "action"

fun HttpExchange.respond(data: ByteArray) {
	this.sendResponseHeaders(200, data.count().toLong())
	val stream = this.getResponseBody()!!
	stream.write(data)
	stream.close()
}

fun HttpExchange.respond(text: String) {
	this.getResponseHeaders()!!.set(ContentTypes.httpHeaderKey, ContentTypes.plainTextUTF_8)
	this.respond(text.getBytesUTF_8())
}

fun getURLQueryArguments(query: String): Map<String, String> {
	val result = HashMap<String, String>()
	val pairs = query.split('&')
	for (pairString in pairs) {
		val pair = pairString.split('=')
		if (pair.count() > 0) {
			val key = URLDecoder.decode(pair[0], "UTF-8")
			val value =
				if (pair.count() > 1)
					URLDecoder.decode(pair[1], "UTF-8")
				else
					""
			result.put(key, value)
		}
	}
	return result
}

fun HttpExchange.getArguments(): Map<String, String> {
	val query = this.getRequestURI()?.getRawQuery()
	if (query != null)
		return getURLQueryArguments(query)
	else
		return HashMap()
}

fun HttpExchange.getRequestFields(): Map<String, String> {
	val requestMethod = this.getRequestMethod()
	var result: Map<String, String> = HashMap()
	if (requestMethod != null)
	{
		if (requestMethod.equalsIgnoreCase("post")) {
			val requestBody = this.getRequestBody()
			if (requestBody != null) {
				val reader = InputStreamReader(requestBody, StandardCharsets.UTF_8)
				val bufferedReader = BufferedReader(reader)
				val query = bufferedReader.readLine()
				if (query != null)
					result = getURLQueryArguments(query)
			}
		}
	}
	return result
}

fun String.getBytesUTF_8(): ByteArray {
	return this.getBytes(StandardCharsets.UTF_8)
}

fun String.getBytesUTF_16(): ByteArray {
	return this.getBytes(StandardCharsets.UTF_16BE)
}

