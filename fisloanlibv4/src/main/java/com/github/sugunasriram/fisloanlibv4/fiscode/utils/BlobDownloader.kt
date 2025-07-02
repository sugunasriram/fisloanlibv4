package com.github.sugunasriram.fisloanlibv4.fiscode.utils

import android.content.Context
import android.media.MediaScannerConnection
import android.os.Environment
import android.util.Base64
import android.webkit.JavascriptInterface
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class BlobDownloader(private val context: Context) {

//    @JavascriptInterface
//    fun downloadBase64File(base64Data: String, mimeType: String, fileName: String) {
//        try {
//            val data = Base64.decode(base64Data.substringAfter(","), Base64.DEFAULT)
//            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
//            FileOutputStream(file).use { it.write(data) }
//
//            // Optional: trigger MediaScanner or open the file
//            Toast.makeText(context, "Downloaded to: ${file.absolutePath}", Toast.LENGTH_LONG).show()
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Toast.makeText(context, "Download failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
//        }
//    }

    @JavascriptInterface
    fun downloadBase64File(base64DataUrl: String, mimeType: String, fileName: String) {
        try {
            val base64Data = base64DataUrl.substringAfter("base64,")
            val fileBytes = Base64.decode(base64Data, Base64.DEFAULT)

            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloadsDir, fileName)

            FileOutputStream(file).use { it.write(fileBytes) }

            // Make file visible in file manager
            MediaScannerConnection.scanFile(context, arrayOf(file.absolutePath), arrayOf(mimeType), null)

            Toast.makeText(context, "Downloaded to Downloads/$fileName", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to save file: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}