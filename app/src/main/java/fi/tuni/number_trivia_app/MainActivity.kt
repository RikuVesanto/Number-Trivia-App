package fi.tuni.number_trivia_app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val randomFactsFragment = RandomFactsFragment()
        val searchFactFragment = SearchFactFragment()
        val saveFactFragment = FavoriteNumberFragment()
        var bottomNavigationView : BottomNavigationView = findViewById(R.id.bottom_navigation)
        supportFragmentManager.beginTransaction().replace(R.id.container, randomFactsFragment).commit()

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.randomFactsFragment -> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, randomFactsFragment).commit()
                }
                R.id.searchFactsFragment-> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, searchFactFragment).commit()
                }
                R.id.saveFactFragment-> {
                    supportFragmentManager.beginTransaction().replace(R.id.container, saveFactFragment).commit()
                }
            }
            return@setOnItemSelectedListener true
        }
    }
}