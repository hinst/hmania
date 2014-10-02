package hmania

import org.joda.time.DateTime
import org.joda.time.Period
import hmania.AH.ActionHandler
import hmania.AH.ActionHandlerMap
import org.simpleframework.http.*
import org.simpleframework.http.core.*
import org.simpleframework.transport.connect.*
import java.net.InetSocketAddress
import hmania.web.respond
import hmania.web.ContentTypes

class WebServer(val settings: WebServerSettings): Container {

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

	val server = ContainerServer(this)

	val logRequestProcessedEnabled = true
	val testResponseText = "Проверка"

	var connection: Connection? = null

	override fun handle(request: Request?, response: Response?) {
		try {
			val respondStartMoment = DateTime();
			respond(request!!, response!!)
			if (logRequestProcessedEnabled)
				Log.emit("HTTP Request '${request.getPath()}' processed, time spent: " + Period(respondStartMoment, DateTime()).toString())
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}

	public fun startServer() {
		Log.emit("Now starting HTTP server; port = ${settings.port}...")
		val address = InetSocketAddress(settings.port)
		val connection = SocketConnection(server)
		connection.connect(address)
		this.connection = connection
	}

	fun respond(request: Request, response: Response) {
		actionRespond(request, response)
	}

	fun prepareActionHandler(actionHandler: ActionHandler) {
		actionHandler.dataMaster = dataMaster
		actionHandler.contentMaster = contentMaster
		actionHandler.userMaster = userMaster
	}

	fun actionRespond(request: Request, response: Response) {
		val action = request.getQuery()!!.get("action") ?: ""
		val actionHandlerCreator = ActionHandlerMap.get(action)
		if (actionHandlerCreator != null) {
			val actionHandler = actionHandlerCreator()
			Log.emit("Action handler for action '${action}' found: '${actionHandler.javaClass.getName()}'")
			actionHandler.action = action
			actionHandler.request = request
			actionHandler.response = response
			prepareActionHandler(actionHandler)
			actionHandler.respond()
		}
		else
			response.respond("ClientMistake: unknown action: '${action}'", ContentTypes.plainText)
	}


}