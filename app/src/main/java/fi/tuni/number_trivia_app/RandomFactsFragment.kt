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
                var numberList =  ArrayList<Int>(getRandomNumbers(5));
                val randomFacts = getListOfFacts(numberList, "http://numbersapi.com/")
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

    private fun getRandomNumbers(amount: Int): Set<Int> {
        return generateSequence { Random.nextInt(1, 100) }
            .distinct()
            .take(amount)
            .sorted()
            .toSet()
    }

    private fun getListOfFacts (numberList: ArrayList<Int>, url: String) : Array<String?> {
        val facts : Array<String?> = arrayOfNulls<String?>(numberList.size)
        for (x in 1..numberList.size) {
            facts[x - 1] = getFact(url + numberList.elementAtOrNull(x))
        }
        return facts
    }

    private fun getFact(apiCall: String) : String {
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