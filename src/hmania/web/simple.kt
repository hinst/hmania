package hmania.web

import org.simpleframework.http.*
import java.io.PrintStream
import java.nio.charset.StandardCharsets

fun Response.respondUTF_8(text: String, contentType: String) {
	this.setContentType(contentType)
	val stream = this.getOutputStream()!!
	val printStream = PrintStream(stream, true, "UTF-8")
	printStream.print(text)
	stream.close()
}
