package hmania.AH

import com.sun.net.httpserver.HttpExchange
import hmania.DataMaster
import hmania.ContentMaster
import hmania.UserMaster
import hmania.User
import hmania.web.getArguments
import java.util.HashMap
import hmania.web.getRequestFields
import org.simpleframework.http.*
import hmania.web.respond
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
	open val pageTitle = ""

	fun respond() {
		actRespond()
	}

	fun recognizeUser() {
	}

	open fun actRespond() {
	}

	fun servePage(title: String, body: String) {
		val responseString = formPage(title, body)
		response.respond(responseString, ContentTypes.htmlText)
	}

	fun formPage(titleString: String, bodyString: String): String {
		val pageTemplateString = contentMaster.loadString(ContentMaster.pageTemplateFileName)
		val template = contentMaster.newTemplate(pageTemplateString)
		template.add(ContentMaster.pageTitleTemplateKey, contentMaster.replace(titleString))
		template.add(ContentMaster.pageBodyTemplateKey, contentMaster.replace(bodyString))
		val fullPage = template.render()
		fullPage!!
		return fullPage
	}

	fun servePage(body: String) {
		servePage(pageTitle, body)
	}

}