package hmania

import java.util.Date
import java.text.SimpleDateFormat
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Level
import java.util.HashMap
import java.io.StringWriter
import java.io.PrintWriter

object Log {

	val defaultDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	val defaultLogLevel = Level.DEBUG

	fun getCurrentMomentString(): String {
		val now = Date()
		val string = defaultDateTimeFormat.format(now)
		return string
	}

	val MarkerLevels: Map<String, Level>
	{
		val map = HashMap<String, Level>()
		map.put("", Level.DEBUG)
		map.put("FATAL:", Level.FATAL)
		map.put("Error:", Level.ERROR)
		map.put("Warning:", Level.WARN)
		map.put("Info:", Level.INFO)
		map.put("Debug:", Level.DEBUG)
		map.put("Trace:", Level.TRACE)
		MarkerLevels = map
	}

	fun getLogLevel(text: String): Level {
		val indexes = HashMap<Level, Int>()
		for (markerLevel in MarkerLevels) {
			val marker = markerLevel.key
			val level = markerLevel.value
			val index = text.indexOf(marker)
			val lowIndex =
				if (index > -1)
					index
				else
					Integer.MAX_VALUE
			indexes.put(level, lowIndex)
		}
		val markerLevel = indexes.minBy {levelIndex -> levelIndex.value}
		val result =
			if (markerLevel != null)
				if (markerLevel.value < Integer.MAX_VALUE)
					markerLevel.key
				else
					defaultLogLevel
			else
				defaultLogLevel
		return result
	}

	fun emit(text: String) {
		val stack = Thread.currentThread().getStackTrace()
		val clazz =
			if (stack.count() > 2)
				stack[2].getClass()
			else
				null
		val logger = LogManager.getLogger(clazz) !!
		val level = getLogLevel(text)
		logger.log(level, text)
	}

	fun emit(e: Throwable): String {
		val writer = StringWriter()
		val printWriter = PrintWriter(writer)
		e.printStackTrace(printWriter)
		printWriter.close()
		return writer.toString()
	}

}