package fi.tuni.number_trivia_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
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
}