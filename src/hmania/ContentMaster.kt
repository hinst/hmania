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
import hmania.web.PageTemplater

// has shutdown method
class ContentMaster {

	val contentSubDirectory = "content"
	val dollarTemplateKey = "dollar"
	val pageTemplateFileName = "pageTemplate.html"
	val accessDeniedFileName = "accessDenied.html"
	val pageTitleTemplateKey = "\$pageTitle$"
	val pageBodyTemplateKey = "\$pageBody$"
	val pageTemplateLoginKey = "pageLogin"
	val hmaniaColor = "#FFD699"
	val hmaniaColorTemplateKey = "\$hmaniaColor$"
	val hmaniaWebDirTemplateKey = "\$hmania$"
	val pageTitleSeparator = "$ pageTitle $"
	val pageTemplateFilePrefix = "file."
	val hmaniaWebDirectory = "hmania"

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
			val key = pageTemplateFilePrefix + file.key
			val fileLoader = file.value
			template.add('$' + key + '$', fileLoader)
		}
	}

	fun newReplacer(): StringReplacer {
		val stringReplacer = StringReplacer()
		addFiles(stringReplacer)
		stringReplacer.add(hmaniaColorTemplateKey, hmaniaColor)
		stringReplacer.add(hmaniaWebDirTemplateKey, hmaniaWebDirectory)
		return stringReplacer
	}

	fun fetch(fileName: String): InputStream {
		return files.get(fileName)!!.fetch()
	}

	fun loadString(fileName: String): String {
		val fileLoader = files.get(fileName)
		val string =
			if (fileLoader != null)
				fileLoader.toString()
			else
				""
		if (null == fileLoader)
			Log.emit("Warning: file loader for '$fileName' not found")
		return string
	}

	fun replace(string: String): String {
		val template = newReplacer()
		val resolvedString = template.replace(string)
		return resolvedString
	}

	fun formPage(content: String): String {
		val templater =
			PageTemplater(
				template = loadString(pageTemplateFileName),
				content = content
			)
		return templater.gen()
	}

	fun shutdown() {
		fileSystem.close()
	}

}