open class WebServerSettings {

    public var port: Int = 0

    public fun setDefaults() {
        port = 8000
    }

}

object DefaultWebServerSettings: WebServerSettings() {
    {
        setDefaults()
    }

}


