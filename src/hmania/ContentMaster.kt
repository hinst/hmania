package hmania

import org.stringtemplate.v4.*
import org.stringtemplate.v4.ST as StringTemplate
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

	fun addFiles(template: StringTemplate) {
		for (file in files) {
			val key = file.key.replace('.', '_')
			val fileLoader = file.value
			template.add(key, fileLoader)
		}
	}

	fun newTemplate(string: String): StringTemplate {
		val st = ST(string, templateStart, templateStop)
		addFiles(st)
		st.add(hmaniaColorTemplateKey, hmaniaColor.toString())
		st.add(dollarTemplateKey, "$");
		return st
	}

	fun fetch(fileName: String): InputStream {
		return files.get(fileName)!!.fetch()
	}

	fun loadString(fileName: String): String {
		val string = files.get(fileName)!!.toString()
		return string
	}

	fun replace(string: String): String {
		val template = newTemplate(string)
		val resolvedString = template.render()
		resolvedString!!
		return resolvedString
	}

	fun shutdown() {
		fileSystem.close()
	}

}