package com.example.nhamngocduc.domain.manager

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class FileDownloader(
    private val context: Context
) {
    suspend fun saveFileToInternalStorage(url: String): String? =
        withContext(Dispatchers.IO) {
            if (url.isEmpty()) {
                return@withContext null
            }

            val fileName = url.substringAfterLast("/", "unknown_file")
            val file = File(context.filesDir, fileName)

            if (file.exists()) {
                return@withContext file.path
            }

            try {
                val connection = URL(url).openConnection()
                connection.connect()
                val inputStream = connection.getInputStream()
                val outputStream = FileOutputStream(file)

                inputStream.copyTo(outputStream)

                outputStream.close()
                inputStream.close()

                return@withContext file.path
            } catch (e: Exception) {
                Log.e("File Downloader", "Fail to download file from URL: $url", e)
                file.delete()
                return@withContext null
            }
        }


}