package hmania

class App {

	public fun run() {
		val webServer = WebServer(DefaultWebServerSettings)
		webServer.startServer()
	}

}