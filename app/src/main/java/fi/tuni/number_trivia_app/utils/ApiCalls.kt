package fi.tuni.number_trivia_app.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ApiCalls {

    fun getListOfFacts (numberList: ArrayList<Int>, url: String) : Array<String?> {
        val facts : Array<String?> = arrayOfNulls<String?>(numberList.size)
        for (x in 1..numberList.size) {
            facts[x - 1] = getFact(url + numberList[x - 1])
        }
        return facts
    }

     fun getFact(apiCall: String) : String {
        var result: String? = null
        val sb = StringBuffer()
        val url = URL(apiCall)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))

        reader.use {
            val line: String? = it.readLine()
            sb.appendLine(line)
        }
        result = sb.toString()
        return result
    }
}