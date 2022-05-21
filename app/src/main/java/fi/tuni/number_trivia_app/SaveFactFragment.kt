package fi.tuni.number_trivia_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
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
        val sharedPrefs = SharedPrefs()
        val myEditText = view.findViewById<View>(R.id.number_input) as EditText
        val button: Button = view.findViewById(R.id.button_id)
        button.setOnClickListener {
            val thread = Thread {
                try {
                    sharedPrefs.saveFavoriteFact(Integer.parseInt(myEditText.text.toString()), activity)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()
        }
        val factList: ListView = view.findViewById(R.id.fact_list)

        val favoriteNumbers = sharedPrefs.getFavoriteFacts(activity)

        val thread = Thread {
            try {
                val apiCalls = ApiCalls()
                val favorites = apiCalls.getListOfFacts(favoriteNumbers,"http://numbersapi.com/")
                activity?.runOnUiThread {
                    val adapter = activity?.let {
                        ArrayAdapter<String?>(
                            it,
                            R.layout.activity_listview, favorites
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
}