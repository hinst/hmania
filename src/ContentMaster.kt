import org.stringtemplate.v4.*
import java.io.InputStream
import sun.misc.Resource
import java.util.ResourceBundle
import java.nio.charset.StandardCharsets

class ContentMaster {

	class object {

		val contentSubDirectory = "content"
		val templateStart = '$'
		val templateStop = '$'
		val bufferSize = 1024

	}

	fun newST(string: String): ST {
		val st = ST(string, templateStart, templateStop)
		return st
	}

	fun load(fileName: String): InputStream {
		val classLoader = Thread.currentThread().getContextClassLoader()!!
		return classLoader.getResourceAsStream(fileName)!!
	}

	fun loadString(fileName: String): String {
		val stream = load(fileName)
		val buffer = ByteArray(bufferSize)
		val stringBuilder = StringBuilder()
		var shouldContinue = true
		while (shouldContinue) {
			val dataLength = stream.read(buffer)
			shouldContinue = dataLength == bufferSize
			if (dataLength > 0) {
				val currentString = String(buffer, 0, dataLength, StandardCharsets.UTF_16)
				stringBuilder.append(currentString)
			}
		}
		return stringBuilder.toString()
	}

}