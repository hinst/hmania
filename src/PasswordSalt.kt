import java.security.SecureRandom

object PasswordSalt {

	val random = SecureRandom.getInstance("SHA1PRNG")
	val saltSize = 16
	var salt: ByteArray = ByteArray(saltSize)

	fun generate(): ByteArray {
		random.nextBytes(salt)
		val result = ByteArray(saltSize)
		System.arraycopy(salt, 0, result, 0, salt.size)
		return result
	}

}