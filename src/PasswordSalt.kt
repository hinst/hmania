import java.security.SecureRandom

object PasswordSalt {

	val random = SecureRandom.getInstance("SHA1PRNG")
	val saltSize = 16
	var salt: ByteArray = ByteArray(saltSize)

	fun generate(): ByteArray {
		val result = ByteArray(saltSize)
		random.nextBytes(salt)
		System.arraycopy(salt, 0, result, 0, salt.size)
		return result
	}

}