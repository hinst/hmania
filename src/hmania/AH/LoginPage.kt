package hmania.AH

import hmania.Log

class LoginPage: ActionHandler() {

	override fun actRespond() {
		val content = contentMaster.loadString("login.html")
		servePage(content)
	}

}