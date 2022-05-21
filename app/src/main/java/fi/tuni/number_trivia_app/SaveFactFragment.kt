package fi.tuni.number_trivia_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class SaveFactFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.save_fact_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val factList: ListView = view.findViewById(R.id.fact_list)
        val favoriteNumbers = getFavoriteFacts()
        val favorites = getListOfFacts(favoriteNumbers,"http://numbersapi.com/")

        val adapter = activity?.let {
            ArrayAdapter<String?>(
                it,
                R.layout.activity_listview, favorites
            )
        }
        factList.adapter = adapter;
        }

    private fun getFavoriteFacts() : ArrayList<Int> {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        var favoritesString = sharedPref?.getString("favorites", "")

        val st = StringTokenizer(favoritesString, ",")
        lateinit var favorites: ArrayList<Int>
        println("token amount:  " + (st.countTokens()).toString())
        for (i in 0 until st.countTokens()) {
            println("token:  " + st.nextToken())
            favorites.add(st.nextToken().toInt())
        }
        return favorites
    }

    private fun saveFavoriteFact(newFavorite: Int) {
        var favorites: ArrayList<Int> = getFavoriteFacts()
        favorites.add(newFavorite)
        val favoriteString = StringBuilder()
        for (i in 0 until favorites.size) {
            favoriteString.append(favorites.get(i)).append(",")
        }
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref?.edit()
        if (editor != null) {
            editor.putString("favorites", favoriteString.toString())
            editor.apply()
        }
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