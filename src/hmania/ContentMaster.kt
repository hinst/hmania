package hmania

import java.io.InputStream
import sun.misc.Resource
import java.util.ResourceBundle
import java.nio.charset.StandardCharsets
import java.nio.file.*
import java.util.HashMap
import java.net.URI
import java.util.ArrayList
import java.io.BufferedReader

// has shutdown method
class ContentMaster {

	class object {

		val contentSubDirectory = "content"
		val templateStart = '$'
		val templateStop = '$'
		val dollarTemplateKey = "dollar"
		val pageTemplateFileName = "pageTemplate.html"
		val pageTitleTemplateKey = "pageTitle"
		val pageBodyTemplateKey = "pageBody"
		val pageTemplateLoginKey = "pageLogin"
		val hmaniaColor = "#C2E0FF"
		val hmaniaColorTemplateKey = "hmaniaColor"
		val pageTitleSeparator = "$ pageTitle $"

	}

	val classLoader = Thread.currentThread().getContextClassLoader()!!
	val contentURI = classLoader.getResource(contentSubDirectory)!!.toURI()
	val contentURIParts = contentURI.toString().split("!")
	val logFileListOnStart = true

	val fileSystem: FileSystem =
		FileSystems.newFileSystem(
			URI.create(contentURIParts[0]),
			HashMap<String, String>()
		)!!
	val files: Map<String, FileLoader>
	{
		val contentURIPath = fileSystem.getPath(contentURIParts[1])
		val directoryStream = Files.newDirectoryStream(contentURIPath)!!
		val files = HashMap<String, FileLoader>()
		for (path in directoryStream) {
			val fileName = path.getFileName().toString()
			if (logFileListOnStart) {
				Log.emit("Content file: '${fileName}' = '${path}")
			}
			val stringLoader = FileLoader(path)
			files.set(fileName, stringLoader)
		}
		this.files = files
		directoryStream.close()
	}

	fun addFiles(template: StringReplacer) {
		for (file in files) {
			val key = file.key.replace('.', '_')
			val fileLoader = file.value
			template.add(key, fileLoader)
		}
	}

	fun newReplacer(): StringReplacer {
		val stringReplacer = StringReplacer()
		addFiles(stringReplacer)
		stringReplacer.add(hmaniaColorTemplateKey, hmaniaColor.toString())
		stringReplacer.add(dollarTemplateKey, "$");
		return stringReplacer
	}

	fun fetch(fileName: String): InputStream {
		return files.get(fileName)!!.fetch()
	}

	fun loadString(fileName: String): String {
		val string = files.get(fileName)!!.toString()
		return string
	}

	fun replace(string: String): String {
		val template = newReplacer()
		val resolvedString = template.replace(string)
		return resolvedString
	}

	fun formPage(string: String): String {
		val pageTitlePosition = string.indexOf(pageTitleSeparator)
		if (pageTitlePosition >= 0) {
			val title = string.substring(0, pageTitlePosition).trim()
			val body = string.substring(pageTitlePosition + pageTitleSeparator.length, string.length)
			return formPage(title, body)
		}
		else {
			return formPage("", string)
		}
	}

	fun formPage(titleString: String, bodyString: String): String {
		val pageTemplateString = loadString(ContentMaster.pageTemplateFileName)
		val template = newReplacer()
		template.add(pageTitleTemplateKey, replace(titleString))
		template.add(pageBodyTemplateKey, replace(bodyString))
		val fullPage = template.replace(pageTemplateString)
		return fullPage
	}

	fun shutdown() {
		fileSystem.close()
	}

}