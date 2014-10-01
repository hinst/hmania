package hmania.AH

import com.sun.net.httpserver.HttpExchange
import hmania.DataMaster
import hmania.ContentMaster
import hmania.UserMaster
import hmania.User
import hmania.getArguments
import java.util.HashMap
import hmania.getRequestFields
import org.simpleframework.http.*

open class ActionHandler {
	var request: Request? = null
	var response: Response? = null
	var dataMaster: DataMaster? = null
	var contentMaster: ContentMaster? = null
	var userMaster: UserMaster? = null
	var currentUser: User? = null
	var fArguments: Map<String, String>? = null

	fun respond() {
		actRespond()
	}

	fun recognizeUser() {
	}

	open fun actRespond() {
	}

}