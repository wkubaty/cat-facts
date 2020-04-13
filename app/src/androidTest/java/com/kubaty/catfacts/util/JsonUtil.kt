package com.kubaty.catfacts.util

import android.app.Application
import android.content.res.AssetManager
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

class JsonUtil @Inject constructor(private val application: Application) {
    fun readJSONFromAsset(fileName: String): String? {
        return try {
            val inputStream: InputStream = (application.assets as AssetManager).open(fileName)
            inputStream.bufferedReader().use { it.readText() }
        } catch (ex: IOException) {
            ex.printStackTrace()
            null
        }
    }
}