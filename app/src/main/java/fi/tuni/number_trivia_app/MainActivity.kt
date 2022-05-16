package fi.tuni.number_trivia_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val thread = Thread {
            try {
                println("Here is a fact about numbers: " + getFacts("http://numbersapi.com/42"))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        thread.start()
    }

    fun getFacts(apiCall: String) : String? {
        var result: String? = null
        val sb = StringBuffer()
        val url = URL(apiCall)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))

        reader.use {
            var line: String? = null
            do {
                line = it.readLine()
                sb.appendLine(line)
            } while (line != null)
            result = sb.toString()
        }
        return result
    }
}