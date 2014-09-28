import java.security.MessageDigest
import org.json.simple.JSONObject

class PasswordHash(val hash: ByteArray, val salt: ByteArray): SaveableJSON {

	class object {

		fun create(password: String): PasswordHash {
			val messageDigest = MessageDigest.getInstance("MD5")
			val salt = PasswordSalt.generate()
			messageDigest.update(salt)
			val hash = messageDigest.digest(password.getBytesUTF16())!!
			return PasswordHash(hash, salt)
		}

	}

	override fun SaveToJSON(): Any {
		val json = JSONObject()
		json.set("hash", hash.toBase64String())
		json.set("salt", salt.toBase64String())
		return json
	}

}