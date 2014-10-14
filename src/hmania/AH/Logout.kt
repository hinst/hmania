package hmania.AH

import hmania.*

class Logout: ActionHandler() {

	override val actionName = "logout"

	override fun actRespond() {
		if (currentUser.access.checkIfAtLeastUser()) {
			userMaster.logOut(dbConnection, currentUser)
			response.setCookie(web.Cookies.sessionID, "")
		}	else {
			serveAccessDeniedPage()
		}
	}

}
