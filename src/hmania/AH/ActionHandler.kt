package hmania.AH

import org.simpleframework.http.*
import hmania.*
import hmania.web.*
import java.util.HashMap
import hmania.Log
import hmania.web.ContentTypes
import java.sql.Connection
import java.io.Closeable

open class ActionHandler: Closeable {

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
	var fDataMaster: DataMaster? = null
	var dataMaster: DataMaster
		get() = fDataMaster!!
		set(value) {
			fDataMaster = value
		}
	var fContentMaster: ContentMaster? = null
	var contentMaster: ContentMaster
		get() = fContentMaster!!
		set(value) {
			fContentMaster = value
		}
	var fUserMaster: UserMaster? = null
	var userMaster: UserMaster
		get() = fUserMaster!!
		set(value) {
			fUserMaster = value
		}
	var currentUser: User? = null

	var fConnection: Connection? = null
	var connection: Connection
		get() {
			if (null == fConnection)
				fConnection = dataMaster.obtainConnection()
			return fConnection!!
		}
		set(value) {
		}

	fun getRequestField(name: String): String {
		val value = request.getQuery()?.get(name)
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
		response.respondUTF_8(responseString, ContentTypes.htmlTextUTF_8)
	}

	override fun close() {
		if (fConnection != null) {
			fConnection!!.close()
			fConnection = null
		}
	}
}