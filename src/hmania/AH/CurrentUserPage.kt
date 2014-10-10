package hmania.AH

class CurrentUserPage: ActionHandler() {

	// cup means Current User Page;
	override val actionName = "cup"

	override fun actRespond() {
		val content = contentMaster.loadString("currentUserPage.html")
		servePage(content)
	}

}