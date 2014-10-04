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

	class object {
		val debugUserRecognition = true;
	}

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
	var currentUser: User = User()

	var fDBConnection: Connection? = null
	var dbConnection: Connection
		get() {
			if (null == fDBConnection)
				fDBConnection = dataMaster.obtainConnection()
			return fDBConnection!!
		}
		set(value) {
		}

	fun getRequestField(name: String): String {
		val value = request.getQuery()?.get(name)
		return value ?: ""
	}

	fun respond() {
		recognizeUser()
		actRespond()
	}

	fun recognizeUser() {
		val userName = request.getCookie(UserMaster.CookieKey.userName)?.getValue()
		if (userName != null) {
			val sessionID = request.getCookie(UserMaster.CookieKey.sessionID)?.getValue()
			if (sessionID != null) {
				currentUser.name = userName
				try {
					currentUser.sessionID = sessionID.toLong()
				}
				catch (e: Exception) {
					currentUser.sessionID = 0
					Log.emit("ClientMistake: sessionID cookie could not be converted to Long: '$sessionID'")
				}
				if (currentUser.sessionID != 0.toLong())
					userMaster.recognize(dbConnection, currentUser)
				if (debugUserRecognition)
					Log.emit("Debug: user '$userName' got access: '${currentUser.access}'")
			}
			else
				Log.emit("ClientMistake: user name cookie present but sessionID cookie not set")
		}
	}

	fun proceedRecognizeUser() {
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
		if (fDBConnection != null) {
			fDBConnection!!.close()
			fDBConnection = null
		}
	}
}