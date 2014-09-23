import com.sun.net.httpserver.*;
import java.io.OutputStream;

val ContentTypeHttpHeaderKey = "Content-Type";
val ContentTypePlainTextUTF8 = "text/plain; charset=utf-8";

fun HttpExchange.respond(data: ByteArray) {
	this.sendResponseHeaders(200, data.count().toLong());
	val stream = this.getResponseBody();
	stream!!.write(data);
	stream.close();
}

fun HttpExchange.respond(text: String) {
	this.getResponseHeaders()!!.set(ContentTypeHttpHeaderKey, ContentTypePlainTextUTF8);
	this.respond(text.getBytes());
}

