package fi.tuni.number_trivia_app.utils

import android.app.Activity
import android.content.Context
import java.util.*

class SharedPrefs {

    /**
     * Retrieves a list of favorited numbers from sharedPreferences.
     *
     * @param activity the activity that is retrieving the list
     * @return an arrayList of numbers
     */

    fun getFavoriteNumbers(activity: Activity?) : ArrayList<Int> {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        var favoritesString = sharedPref?.getString("favorites", "")

        val st = StringTokenizer(favoritesString, ",")
        var favorites = ArrayList<Int>()
        for (i in 0 until st.countTokens()) {
            favorites.add(Integer.parseInt(st.nextToken()))
        }
        return favorites
    }

    /**
     * Saves a number to the list of favourite numbers in sharedPreferences.
     * @param newFavorite the number that is to be added as a favorite
     * @param activity the activity that made the call to save the number
     */

    fun saveFavoriteNumbers(newFavorite: Int, activity: Activity?) {
        val favorites: ArrayList<Int> = getFavoriteNumbers(activity)
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