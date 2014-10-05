package hmania.AH

import hmania.*
import hmania.web.*

class HttpDiagnostics: ActionHandler() {

	override val actionName = "hd"

	override fun actRespond() {
		// response text string builder
		val s = StringBuilder()
		s.appendln("HTTP diagnostics page")
		s.appendln("Address: '${request.getAddress()}'")
		s.appendln("Query fields: " + mapToDebugText(request.getQuery()))
		response.respondUTF_8(s.toString(), ContentTypes.plainText)
	}

}