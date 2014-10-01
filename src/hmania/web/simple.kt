package hmania.web

import org.simpleframework.http.*

fun Response.respond(text: String, contentType: String) {
	this.setContentType(contentType)
	val stream = this.getPrintStream()!!
	stream.print(text)
	stream.close()
}
