package ru.dellin.core.api.cache.impl

import android.content.Context
import ru.dellin.core.api.cache.FileManager
import java.io.ByteArrayOutputStream
import java.io.InputStream

class AndroidAssetsFileManager(private val context: Context) : FileManager {

  override fun string(file: String): String {
    var stringFile = ""

    byteArrayOutputStream(file) { inputStream, byteArrayOutputStream ->
      val buffer = ByteArray(1024)
      var length: Int = inputStream.read(buffer)

      while (length != -1) {
        byteArrayOutputStream.write(buffer, 0, length)
        length = inputStream.read(buffer)
      }

      stringFile = byteArrayOutputStream.toString("UTF-8")
    }

    return stringFile
  }

  override fun byteArrayOutputStream(
    file: String,
    block: (InputStream, ByteArrayOutputStream) -> Unit
  ) {
    readFile(file) { inputStream ->
      ByteArrayOutputStream().use { byteArrayOutputStream ->
        block(inputStream, byteArrayOutputStream)
      }
    }
  }

  override fun <R> readFile(
    file: String,
    block: (InputStream) -> R
  ): R = context.assets
    .open(file)
    .use { block(it) }
}
