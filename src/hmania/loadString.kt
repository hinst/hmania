package hmania

import java.io.InputStream
import java.nio.charset.StandardCharsets

fun loadStringUTF_8(stream: InputStream, bufferSize: Int): String {
	val buffer = ByteArray(bufferSize)
	val stringBuilder = StringBuilder()
	var shouldContinue = true
	while (shouldContinue) {
		val dataLength = stream.read(buffer)
		shouldContinue = dataLength == bufferSize
		if (dataLength > 0) {
			val currentString = String(buffer, 0, dataLength, StandardCharsets.UTF_8)
			stringBuilder.append(currentString)
		}
	}
	return stringBuilder.toString()

}