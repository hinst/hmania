package hmania.AH

import hmania.*
import hmania.web.*

class Login : ActionHandler() {

	override val actionName = "l"

	var name: String = ""
	var password: String = ""

	override fun actRespond() {
		name = getRequestField("name")
		password = getRequestField("password")
		if ("" != name) {
			if ("" != password) {
				logIn()
			} else {
				Log.emit("ClientMistake: password request field not specified")
				respond2("password request field not set")
			}
		} else {
			Log.emit("ClientMistake: name request field not specified")
			respond2("name request field not set")
		}
	}

	fun respond2(loginAttemptResultText: String) {
		val replacer = newReplacer()
		replacer.add("\$loginAttemptResult$", loginAttemptResultText)
		replacer.add("\$loginAttemptUser$", name)
		replacer.add("\$loginAttemptPassword$", getAsterisks(password))
		val page =
			replacer.replace(
				contentMaster.loadString("loginResult.html")
			)
		servePage(page)
	}

	fun logIn() {
		val user = User()
		user.name = name
		user.password = password
		userMaster.logIn(dbConnection, user)
		if (user.access != User.Access.No) {
			response.setCookie(Cookies.userName, user.name)
			response.setCookie(Cookies.sessionID, user.sessionID.toString())
		}
		respond2("your access is " + user.access)
	}

}
