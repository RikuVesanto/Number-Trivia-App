package fi.tuni.number_trivia_app

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myEditText = findViewById<View>(R.id.number_input) as EditText
        val searchResult: TextView = findViewById(R.id.search_result)
        val button: Button = findViewById(R.id.button_id)
        button.setOnClickListener {
            val thread = Thread {
                try {
                    val result = getFacts("http://numbersapi.com/" + myEditText.text.toString())
                    this@MainActivity.runOnUiThread {
                        searchResult.text = result
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()
        }
        val factList: ListView = findViewById(R.id.fact_list)
        val thread = Thread {
            try {
                val randomFacts = getRandomFacts(5, "http://numbersapi.com/")
                this@MainActivity.runOnUiThread {
                    val adapter = ArrayAdapter<String?>(
                        this,
                        R.layout.activity_listview, randomFacts
                    )
                    factList.adapter = adapter;
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }

    private fun getRandomFacts (amount: Int, url: String) : Array<String?> {
        var randomFacts : Array<String?> = arrayOfNulls<String?>(amount)
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

    private fun getFacts(apiCall: String) : String? {
        var result: String? = null
        val sb = StringBuffer()
        val url = URL(apiCall)
        val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
        val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))

        reader.use {
            var line: String? = it.readLine()
                    sb.appendLine(line)
        }
        result = sb.toString()
        return result
    }
}