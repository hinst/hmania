package hmania

import com.sun.net.httpserver.*
import java.net.InetSocketAddress
import org.joda.time.DateTime
import org.joda.time.Period
import hmania.AH.ActionHandler
import hmania.AH.ActionHandlerMap

class WebServer(val settings: WebServerSettings) : HttpHandler {

	val dataMaster: DataMaster
	{
		val settings = DataBaseSettings()
		settings.loadFromJSONFile(DataBaseSettings.defaultFilePath)
		Log.emit("DB settings are: " + settings.toString())
		dataMaster = DataMaster(settings)
	}

	val userMaster: UserMaster
	{
		userMaster = UserMaster(dataMaster)
		val webSiteAdmin = User()
		webSiteAdmin.loadFromJSONFile(UserMaster.defaultAdminSettingsFilePath)
		webSiteAdmin.encodePassword()
		userMaster.ensureUser(webSiteAdmin)
	}

	val contentMaster: ContentMaster
	{
		contentMaster = ContentMaster()
	}

	val server: HttpServer
	{
		Log.emit("Now creating HTTP server; port = ${settings.port}...")
		server = HttpServer.create(InetSocketAddress(settings.port), 1)!!
		server.createContext("/hmania", this)
		server.setExecutor(null)
	}

	val logRequestProcessedEnabled = true
	val testResponseText = "Проверка"

	public fun startServer() {
		Log.emit("Now starting HTTP server...")
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
		actionRespond(exchange)
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

	fun prepareActionHandler(actionHandler: ActionHandler) {
		actionHandler.dataMaster = dataMaster
		actionHandler.contentMaster = contentMaster
		actionHandler.userMaster = userMaster
	}

	fun actionRespond(exchange: HttpExchange) {
		val arguments = exchange.getArguments()
		val action = arguments.get(actionURLArgumentKey)?: ""
		val actionHandlerCreator = ActionHandlerMap.get(action)
		if (actionHandlerCreator != null) {
			val actionHandler = actionHandlerCreator()
			Log.emit("Action handler for action '${action}' found: '${actionHandler.javaClass.getName()}'")
			actionHandler.exchange = exchange
			prepareActionHandler(actionHandler)
			actionHandler.respond()
		}
		else
			exchange.respond("ClientMistake: unknown action: '${action}'")
	}


}