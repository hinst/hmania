package hmania.AH

import hmania.*
import com.sun.net.httpserver.HttpExchange

class HttpDiagnostics: ActionHandler() {

	override fun actRespond() {
		val exchange = exchange!!
		// response text string builder
		val responseText_sb = StringBuilder()
		responseText_sb.append("HTTP diagnostics page" + PageLineEnding)
		responseText_sb.append(exchange.getArgumentsAsDebugText())
		exchange.respond(responseText_sb.toString())
	}

}