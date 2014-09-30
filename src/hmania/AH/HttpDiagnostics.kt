package hmania.AH

import hmania.*
import com.sun.net.httpserver.HttpExchange

class HttpDiagnostics: ActionHandler() {

	override fun actRespond() {
		val exchange = exchange!!
		// response text string builder
		val responseText_sb = StringBuilder()
		responseText_sb.appendln("HTTP diagnostics page")
		responseText_sb.appendln("URL arguments: " + mapToDebugText(arguments))
		responseText_sb.appendln("Request headers: " + mapToDebugText(exchange.getRequestHeaders()))
		responseText_sb.appendln("Request fields: " + mapToDebugText(requestFields))
		exchange.respond(responseText_sb.toString())
	}

}