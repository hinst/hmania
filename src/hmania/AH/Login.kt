package hmania.AH

import hmania.getArguments
import hmania.Log

class Login: ActionHandler() {

	override fun actRespond() {
		val exchange = exchange!!
		val arguments = arguments!!
		val name = arguments.get("name")
		val password = arguments.get("password")
		if (null != name) {
			if (null != password) {
				logIn(name, password)
			}
			else {
				Log.emit("ClientMistake: password URL argument not specified")
			}
		}
		else {
			Log.emit("ClientMistake: name URL argument not specified")
		}
		Log.emit("User attempts to log in: ${name}")
	}

	fun logIn(name: String, password: String) {

	}

}
