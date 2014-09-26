import java.security.MessageDigest

class PasswordHash(val hash: ByteArray, val salt: ByteArray) {

	class object {

		fun create(password: String): PasswordHash {
			val messageDigest = MessageDigest.getInstance("MD5")
			val salt = PasswordSalt.generate()
			messageDigest.update(salt)
			val hash = messageDigest.digest(password.getBytesUTF16())!!
			return PasswordHash(hash, salt)
		}

	}

}