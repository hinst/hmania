package hmania.AH

import hmania.User

class CurrentUserPage : ActionHandler() {

	// cup means Current User Page;
	override val actionName = "cup"

	override fun actRespond() {
		if (currentUser.access.checkIfAtLeastUser()) {
			val content = contentMaster.loadString("currentUserPage.html")
			servePage(content)
		} else {
			serveAccessDeniedPage()
		}
	}

}