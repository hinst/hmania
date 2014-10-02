package hmania.AH

import hmania.web.getArguments
import hmania.Log
import hmania.mapToDebugText

class Login: ActionHandler() {

	override fun actRespond() {
		val name = getRequestField("name")
		val password = getRequestField("password")
		if ("" != name) {
			if ("" != password) {
				logIn(name, password)
			}
			else {
				Log.emit("ClientMistake: password request field not specified")
			}
		}
		else {
			Log.emit("ClientMistake: name request field not specified")
		}
	}

	fun logIn(name: String, password: String) {
		Log.emit("${name} attempts to log in")
	}

}
