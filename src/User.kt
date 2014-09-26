class User(val name: String) {

	class object {
		val NoAccess = 0
		val AccessUser = 1
		val AccessAdmin = 2
	}

	public var password: String = ""
	public var access: Int = 0
	public var sessionID: Long = 0

}