package hmania.web

object ContentTypes {

	val plainText = "text/plain";
	val htmlText = "text/html"
	val httpHeaderKey = "Content-Type"
	val plainTextUTF_8 = "${plainText}; charset=utf-8"
	/** Java default internal charset is UTF-16 */
	val plainTextUTF_16 = "${plainText}; charset=utf-16"

}