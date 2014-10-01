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
	var contentMaster: ContentMaster? = null
	var userMaster: UserMaster? = null
	var currentUser: User? = null

	fun respond() {
		actRespond()
	}

	fun recognizeUser() {
	}

	open fun actRespond() {
	}

}