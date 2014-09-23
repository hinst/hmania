open class WebServerSettings {

    public var port: Int = 0
        private set

    public fun setDefaults() {
        port = 8000
    }

}

object DefaultWebServerSettings: WebServerSettings() {
    {
        setDefaults()
    }

}


