package hmania.AH

import com.sun.net.httpserver.HttpExchange
import hmania.DataMaster
import hmania.ContentMaster
import hmania.UserMaster
import hmania.User
import hmania.getArguments
import java.util.HashMap
import hmania.getRequestFields

open class ActionHandler {

	var exchange: HttpExchange? = null
	var dataMaster: DataMaster? = null
	var contentMaster: ContentMaster? = null
	var userMaster: UserMaster? = null
	var currentUser: User? = null
	var fArguments: Map<String, String>? = null
	var arguments: Map<String, String> = HashMap()
		get() {
			if (null == fArguments) {
				fArguments = exchange!!.getArguments()
			}
			return fArguments!!
		}
	var fRequestFields: Map<String, String>? = null
	var requestFields: Map<String, String> = HashMap()
		get() {
			if (null == fRequestFields) {
				fRequestFields = exchange!!.getRequestFields()
			}
			return fRequestFields!!
		}

	fun respond() {
		actRespond()
	}

	fun recognizeUser() {
	}

	open fun actRespond() {
	}

}