package fi.tuni.number_trivia_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlin.random.Random
import android.widget.*
import fi.tuni.number_trivia_app.utils.ApiCalls;


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
                val numberList =  ArrayList<Int>(getRandomNumbers(5));
                val apiCalls = ApiCalls()
                val randomFacts = apiCalls.getListOfFacts(numberList, "http://numbersapi.com/")
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

    /**
     * Randomizes a given amount of numbers and returns a number set of them with no duplicates
     *
     * @param amount the amount of numbers that are to be randomized
     * @return a randomized set of Int
     */

    private fun getRandomNumbers(amount: Int): Set<Int> {
        return generateSequence { Random.nextInt(1, 100) }
            .distinct()
            .take(amount)
            .sorted()
            .toSet()
    }
}