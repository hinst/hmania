package hmania.web

import hmania.StringReplacer

class PageTemplater(val template: String, var content: String) {

	fun gen(): String {
		val title = tagExtract("title")
		val style = tagExtract("style")
		val replacer = StringReplacer()
		replacer.add("\$title$", title)
		replacer.add("\$style$", style)
		val composedText = replacer.replace(template)
		return composedText
	}

	fun extract(a: String, b: String): String {
		val aIndex = content.indexOf(a)
		val bIndex = content.indexOf(b)
		if ((aIndex >= 0) && (bIndex >= 0)) {
			val result = content.substring(aIndex + a.length, bIndex)
			content =
				content.substring(0, aIndex) + content.substring(bIndex + b.length, content.length)
			return result
		}
		else
			return ""
	}

	fun tagExtract(tag: String): String {
		return extract("<" + tag + ">", "<" + tag + "/>")
	}

}