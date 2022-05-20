package fi.tuni.number_trivia_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random
import android.widget.*

class RandomFactsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.random_facts_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val factList: ListView = view.findViewById(R.id.fact_list)
        val thread = Thread {
            try {
                val randomFacts = getRandomFacts(5, "http://numbersapi.com/")
                activity?.runOnUiThread {

                    val adapter = activity?.let {
                        ArrayAdapter<String?>(
                        it,
                        R.layout.activity_listview, randomFacts
                    )
                    }
                    factList.adapter = adapter;
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private fun getRandomFacts (amount: Int, url: String) : Array<String?> {
        val randomFacts : Array<String?> = arrayOfNulls<String?>(amount)
        val randomInts = generateSequence { Random.nextInt(1,100) }
            .distinct()
            .take(6)
            .sorted()
            .toSet()
        for (x in 1..amount) {
            randomFacts[x - 1] = getFacts(url + randomInts.elementAtOrNull(x))
        }
        return randomFacts
    }

    private fun getFacts(apiCall: String) : String {
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