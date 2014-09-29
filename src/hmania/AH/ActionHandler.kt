package hmania.AH

import com.sun.net.httpserver.HttpExchange
import hmania.DataMaster
import hmania.ContentMaster
import hmania.UserMaster
import hmania.User

open class ActionHandler {

	var exchange: HttpExchange? = null
	var dataMaster: DataMaster? = null
	var contentMaster: ContentMaster? = null
	var userMaster: UserMaster? = null
	var currentUser: User? = null

	fun respond() {

	}

	fun recognizeUser() {
	}

	open fun actRespond() {

	}

}