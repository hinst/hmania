package hmania.web

import org.simpleframework.http.*

fun Response.respond(text: String) {
	this.setContentType(contentTypeTextPlain)
	val stream = this.getPrintStream()!!
	stream.print(text)
	stream.close()
}