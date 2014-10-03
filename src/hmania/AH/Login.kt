package hmania.AH

import hmania.web.getArguments
import hmania.Log
import hmania.mapToDebugText
import hmania.User
import hmania.web.respond
import hmania.web.ContentTypes
import hmania.web.getAsterisks
import hmania.UserMaster

class Login: ActionHandler() {

	var name: String = ""
	var password: String = ""

	override fun actRespond() {
		name = getRequestField("name")
		password = getRequestField("password")
		if ("" != name) {
			if ("" != password) {
				logIn()
			}
			else {
				Log.emit("ClientMistake: password request field not specified")
				respond2("password request field not set")
			}
		}
		else {
			Log.emit("ClientMistake: name request field not specified")
			respond2("name request field not set")
		}
	}

	fun respond2(loginAttemptResultText: String) {
		val replacer = newReplacer()
		replacer.add("loginAttemptResult", loginAttemptResultText)
		replacer.add("loginAttemptUser", name)
		replacer.add("loginAttemptPassword", getAsterisks(password))
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
			response.setCookie(UserMaster.CookieKey.userName, user.name)
			response.setCookie(UserMaster.CookieKey.sessionID, user.sessionID.toString())
		}
		respond2("your access is " + user.access)
	}

}
