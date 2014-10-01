package hmania.AH

import hmania.*

class HttpDiagnostics: ActionHandler() {

	override fun actRespond() {
		// response text string builder
		val responseText_sb = StringBuilder()
		responseText_sb.appendln("HTTP diagnostics page")
		responseText_sb.appendln("Address: " + request!!.getAddress())
	}

}