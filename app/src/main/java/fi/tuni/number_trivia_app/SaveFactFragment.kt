package fi.tuni.number_trivia_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*
import fi.tuni.number_trivia_app.utils.ApiCalls;
import fi.tuni.number_trivia_app.utils.SharedPrefs;

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
        val sharedPrefs = SharedPrefs()
        val favoriteNumbers = sharedPrefs.getFavoriteFacts(activity)

        var apiCalls = ApiCalls()
        val favorites = apiCalls.getListOfFacts(favoriteNumbers,"http://numbersapi.com/")

        val adapter = activity?.let {
            ArrayAdapter<String?>(
                it,
                R.layout.activity_listview, favorites
            )
        }
        factList.adapter = adapter;
        }
}