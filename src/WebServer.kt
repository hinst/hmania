import com.sun.net.httpserver.*
import java.net.InetSocketAddress
import org.joda.time.DateTime
import org.joda.time.Period

class WebServer(val settings: WebServerSettings): HttpHandler {

	val logRequestProcessedEnabled = true;

	public fun run() {
		runServer()
	}

	fun runServer() {
		Log.emit("Now creating HTTP server; port = ${settings.port}")
		val server = HttpServer.create(InetSocketAddress(settings.port), 1)
		server!!.createContext("/hmania", this)
		server.setExecutor(null)
		server.start()
	}

	override fun handle(exchange: HttpExchange?) {
		val respondStartMoment = DateTime();
		respond(exchange!!);
		val requestString = exchange.getRequestURI()?.getPath();
		if (logRequestProcessedEnabled)
			Log.emit("HTTP Request '${requestString}' processed, time spent: " + Period(respondStartMoment, DateTime()).toString())
	}

	fun respond(exchange: HttpExchange) {
		testRespond(exchange)
	}

	fun testRespond(exchange: HttpExchange) {
		val response = StringBuilder()
		response.append(testResponseText + PageLineEnding)
		val arguments = exchange.getArguments()
		response.append("Count of URL arguments: ${arguments.count()}${PageLineEnding}")
		for (argument in arguments) {
			response.append("'${argument.key}' = '${argument.value}'${PageLineEnding}")
		}
		exchange.respond(response.toString())
	}

	val testResponseText = "Проверка"

}