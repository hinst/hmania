import com.sun.net.httpserver.*;
import java.net.InetSocketAddress

class WebServer(val settings: WebServerSettings): HttpHandler {

	public fun run() {
		val server = HttpServer.create(InetSocketAddress(settings.port), 1);
		server!!.createContext("/hmania", this);
	}

	override fun handle(exchange: HttpExchange?) {
		respond(exchange!!);
	}

	fun respond(exchange: HttpExchange) {
		testRespond(exchange);
	}

	fun testRespond(exchange: HttpExchange) {
		exchange.respond(testResponseText);
	}

	val testResponseText = "Азаза затралил";

}