package hmania

import java.nio.file.Path
import java.nio.file.Files
import java.io.InputStream

class FileLoader(val path: Path) {

	val bufferSize = 1024

	fun loadString(): String {
		val stream = fetch()
		val string = loadStringUTF_8(stream, bufferSize)
		stream.close()
		return string
	}

	fun fetch(): InputStream {
		return Files.newInputStream(path)
	}

	override fun toString(): String {
		return loadString()
	}

}