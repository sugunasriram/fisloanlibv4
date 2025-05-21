package com.github.sugunasriram.fisloanlibv4.fis_code.utils

import android.annotation.SuppressLint
import android.os.Environment
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileLogger {
     const val FOLDER_NAME = "Fs"
     private const val API_FILE_NAME = "API_Logs.txt"
     private const val SSE_FILE_NAME = "SSE_Logs.txt"

    @SuppressLint("SuspiciousIndentation")
    fun writeToFile(content: String, isApiLong : Boolean) {
        try {
        val folder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), FOLDER_NAME)
            if(!folder.exists()){
                folder.mkdirs()
            }

        val file = if (isApiLong) {
                File(folder, API_FILE_NAME)
            } else {
                File(folder, SSE_FILE_NAME)
            }

            val logTime = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            val fileWriter = BufferedWriter(FileOutputStream(file, true).bufferedWriter())
            fileWriter.write(String.format(Locale.getDefault(), "[%s] %s%n", logTime, content))
            fileWriter.write("\t")
            fileWriter.close()
        } catch (e: Exception) {
//            e.printStackTrace()
        }
    }

    
}
