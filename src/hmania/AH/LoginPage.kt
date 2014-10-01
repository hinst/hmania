package hmania.AH

import org.stringtemplate.v4.ST
import hmania.Log

class LoginPage: ActionHandler() {

	override val pageTitle = "Login"

	override fun actRespond() {
		val content = contentMaster.loadString("login.html")
		servePage(content)
	}

}