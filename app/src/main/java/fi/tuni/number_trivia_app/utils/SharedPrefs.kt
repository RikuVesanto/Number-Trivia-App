package fi.tuni.number_trivia_app.utils

import android.app.Activity
import android.content.Context
import java.util.*

class SharedPrefs {

    fun getFavoriteFacts(activity: Activity?) : ArrayList<Int> {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        var favoritesString = sharedPref?.getString("favorites", "")

        val st = StringTokenizer(favoritesString, ",")
        var favorites = ArrayList<Int>()
        for (i in 0 until st.countTokens()) {
            favorites.add(Integer.parseInt(st.nextToken()))
        }
        return favorites
    }

    fun saveFavoriteFact(newFavorite: Int, activity: Activity?) {
        val favorites: ArrayList<Int> = getFavoriteFacts(activity)
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