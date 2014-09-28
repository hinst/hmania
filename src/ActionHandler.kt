import com.sun.net.httpserver.HttpExchange

open class ActionHandler {

	var exchange: HttpExchange? = null
	var dataMaster: DataMaster? = null
	var contentMaster: ContentMaster? = null
	var userMaster: UserMaster? = null

	open fun respond() {

	}

}