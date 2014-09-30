package hmania.AH

import hmania.*
import com.sun.net.httpserver.HttpExchange

class HttpDiagnostics: ActionHandler() {

	override fun actRespond() {
		val exchange = exchange!!
		// response text string builder
		val responseText_sb = StringBuilder()
		responseText_sb.appendln("HTTP diagnostics page")
		responseText_sb.appendln("URL arguments:\n" + mapToDebugText(arguments))
		responseText_sb.append("Request headers:\n" + mapToDebugText(exchange.getRequestHeaders()))
		responseText_sb.append("Request fields:\n" + mapToDebugText(requestFields))
		exchange.respond(responseText_sb.toString())
	}

}