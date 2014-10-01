package hmania.AH

import org.simpleframework.http.*
import hmania.*
import hmania.web.*
import java.util.HashMap
import hmania.Log
import hmania.web.ContentTypes

open class ActionHandler {
	var fRequest: Request? = null
	var request: Request
		get() = fRequest!!
		set(value) {
			fRequest = value
		}
	var fResponse: Response? = null
	var response: Response
		get() = fResponse!!
		set(value) {
			fResponse = value
		}
	var dataMaster: DataMaster? = null
	var fContentMaster: ContentMaster? = null
	var contentMaster: ContentMaster
		get() = fContentMaster!!
		set(value) {
			fContentMaster = value
		}
	var userMaster: UserMaster? = null
	var currentUser: User? = null

	fun respond() {
		actRespond()
	}

	fun recognizeUser() {
	}

	open fun actRespond() {
	}

	fun servePage(page: String) {
		val responseString = contentMaster.formPage(page)
		response.respond(responseString, ContentTypes.htmlText)
	}

}