package hmania

import org.stringtemplate.v4.*
import java.io.InputStream
import sun.misc.Resource
import java.util.ResourceBundle
import java.nio.charset.StandardCharsets
import java.nio.file.*
import java.util.HashMap
import java.net.URI
import java.util.ArrayList

class ContentMaster {

	class object {

		val contentSubDirectory = "content"
		val templateStart = '$'
		val templateStop = '$'
		val bufferSize = 1024

	}

	val classLoader = Thread.currentThread().getContextClassLoader()!!
	val contentURI = classLoader.getResource(contentSubDirectory)!!.toURI()
	val contentURIParts = contentURI.toString().split("!")
	val logFileListOnStart = true

	inner class StringLoader(val fileName: String) {

		override fun toString(): String {
			return loadString(fileName)
		}

	}

	val files: Map<String, StringLoader>
	{
		val fileSystem: FileSystem =
			FileSystems.newFileSystem(
				URI.create(contentURIParts[0]),
				HashMap<String, String>()
			)!!
		val contentURIPath = fileSystem.getPath(contentURIParts[1])
		val directoryStream = Files.newDirectoryStream(contentURIPath)!!
		val files = HashMap<String, StringLoader>()
		for (path in directoryStream) {
			val fileName = path.getFileName().toString()
			if (logFileListOnStart) {
				Log.emit("Content file: '${path}'")
			}
			val stringLoader = StringLoader(fileName)
			files.set(fileName, stringLoader)
		}
		this.files = files
		directoryStream.close()
		fileSystem.close()
	}

	fun newST(string: String): ST {
		val st = ST(string, templateStart, templateStop)
		return st
	}

	fun load(fileName: String): InputStream {
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