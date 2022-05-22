package fi.tuni.number_trivia_app.utils

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ApiCalls {

    /**
     * Gets a list of number facts.
     *
     * @param numberList list of numbers that are retrieved from the api
     * @param url the base url for the api call
     * @return an arrayList of number facts
     */

    fun getListOfFacts (numberList: ArrayList<Int>, url: String) : Array<String?> {
        val facts : Array<String?> = arrayOfNulls<String?>(numberList.size)
        for (x in 1..numberList.size) {
            facts[x - 1] = getFact(url + numberList[x - 1])
        }
        return facts
    }

    /**
     * Retrieves data from the given api url.
     *
     * @param apiCall url for the api
     * @return the retrieved text string from the api
     */

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