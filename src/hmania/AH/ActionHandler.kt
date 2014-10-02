package hmania.AH

import org.simpleframework.http.*
import hmania.*
import hmania.web.*
import java.util.HashMap
import hmania.Log
import hmania.web.ContentTypes

open class ActionHandler {
	var action: String = ""
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

	fun getRequestField(name: String): String {
		val value = request.getQuery()?.get("name")
		return value ?: ""
	}

	fun respond() {
		actRespond()
	}

	fun recognizeUser() {
	}

	open fun actRespond() {
	}

	fun newReplacer(): StringReplacer {
		val replacer = contentMaster.newReplacer()
		replacer.add("action", action)
		return replacer
	}

	fun replace(string: String): String {
		return newReplacer().replace(string)
	}

	fun servePage(page: String) {
		val responseString = replace(contentMaster.formPage(page))
		response.respond(responseString, ContentTypes.htmlText)
	}

}