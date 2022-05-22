package fi.tuni.number_trivia_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import fi.tuni.number_trivia_app.utils.ApiCalls;

class SearchFactFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fact_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        val myEditText = view.findViewById<View>(R.id.number_input) as EditText
        val searchResult: TextView = view.findViewById(R.id.search_result)
        val button: Button = view.findViewById(R.id.button_id)
        button.setOnClickListener {
            val thread = Thread {
                try {
                    if (myEditText.text.toString() != "") {
                        val apiCalls = ApiCalls()
                        val result = apiCalls.getFact("http://numbersapi.com/" + myEditText.text.toString())
                        activity?.runOnUiThread {
                            searchResult.text = result
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()
        }
    }
}