package hmania

import sun.misc.BASE64Encoder
import sun.misc.BASE64Decoder

fun ByteArray.toBase64String(): String {
	val encoder = BASE64Encoder()
	val string = encoder.encode(this)!!
	return string
}

fun createByteArrayFromBase64String(string: String): ByteArray {
	val decoder = BASE64Decoder()
	val array = decoder.decodeBuffer(string)!!
	return array
}