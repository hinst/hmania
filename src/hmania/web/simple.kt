package hmania.web

import org.simpleframework.http.*
import java.io.PrintStream
import java.nio.charset.StandardCharsets

fun Response.respond(text: String, contentType: String) {
	val charset = "UTF-8"
	this.setContentType(contentType + "; charset=" + charset)
	val stream = this.getOutputStream()!!
	val printStream = PrintStream(stream, true, charset)
	printStream.print(text)
	stream.close()
}
