import java.util.Date;
import java.text.SimpleDateFormat;

// Dumb Log
object Log {

	val defaultDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	fun getCurrentMomentString(): String {
		val now = Date()
		val string = defaultDateTimeFormat.format(now)
		return string
	}

	fun emit(text: String) {
		val moment = getCurrentMomentString()
		val stack = Thread.currentThread().getStackTrace()
		val className =
			if (stack.count() > 2)
				stack[2].getClassName()
			else
				""
		val threadID = Thread.currentThread().getId().toString()
		println(" # ${threadID} ${moment} ${className}: ${text}")
	}

}