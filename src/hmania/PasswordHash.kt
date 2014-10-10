package hmania

import java.security.MessageDigest
import org.json.simple.JSONObject
import hmania.web.*
import java.nio.charset.StandardCharsets

class PasswordHash(): SaveableJSON, LoadableJSON {

	class object {

		val charset = StandardCharsets.UTF_16

	}

	var hash: ByteArray = ByteArray(0)
	var salt: ByteArray = ByteArray(0)

	fun createFromPassword(password: String) {
		val messageDigest = MessageDigest.getInstance("MD5")
		salt = PasswordSalt.generate()
		messageDigest.update(salt)
		hash = messageDigest.digest(password.getBytes(charset)) !!
	}

	fun checkPassword(password: String): Boolean {
		val messageDigest = MessageDigest.getInstance("MD5")
		messageDigest.update(salt)
		val hash = messageDigest.digest(password.getBytes(charset)) !!
		val hashEqual = hash.toBase64String() == this.hash.toBase64String()
		return hashEqual
	}

	override fun saveToJSON(): Any {
		val json = JSONObject()
		json.set("hash", hash.toBase64String())
		json.set("salt", salt.toBase64String())
		return json
	}

	override fun loadFromJSON(json: Any) {
		json as JSONObject
		hash = createByteArrayFromBase64String(json.get("hash") as String)
		salt = createByteArrayFromBase64String(json.get("salt") as String)
	}

}