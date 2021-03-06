package org.mousehole.americanairline.simpleomdbsearch.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import org.mousehole.americanairline.simpleomdbsearch.R
import org.mousehole.americanairline.simpleomdbsearch.model.SeasonData
import org.mousehole.americanairline.simpleomdbsearch.util.Constants
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.LOG_TAG
import org.mousehole.americanairline.simpleomdbsearch.viewmodel.MovieViewModel
import org.mousehole.americanairline.simpleomdbsearch.viewmodel.SeasonViewModel
import org.mousehole.americanairline.simpleomdbsearch.viewmodel.SeriesViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var mainFrameLayout: FrameLayout
    private lateinit var searchButton: Button
    private lateinit var typeRadioGroup: RadioGroup
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var mainLayout : ConstraintLayout

    private val seriesFragment: SeriesFragment = SeriesFragment()
    private val seriesViewModel: SeriesViewModel by viewModels()

    private val movieFragment: MovieFragment = MovieFragment()
    private val movieViewModel: MovieViewModel by viewModels()

    private val seasonFragment: SeasonFragment = SeasonFragment()
    private val seasonViewModel : SeasonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        typeRadioGroup = findViewById(R.id.type_radiogroup)

        searchEditText = findViewById(R.id.search_textinput)

        searchButton = findViewById(R.id.search_button)

        searchButton.setOnClickListener {
            when (typeRadioGroup.checkedRadioButtonId) {
                R.id.movie_radiobutton ->
                    addFragment(
                            movieFragment,
                            movieFragment::changeMovie,
                            movieViewModel::getMovieResult,
                            "movie"
                    )
                else ->
                    addFragment(
                            seriesFragment,
                            seriesFragment::changeSeries,
                            seriesViewModel::getSeriesResult,
                            "series"
                    )
            }
            val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(mainLayout.windowToken, 0)
        }
        drawerLayout = findViewById(R.id.search_drawerlayout)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        mainFrameLayout = findViewById(R.id.main_frame)

        mainLayout = findViewById(R.id.main_layout)

    }

    private fun <T> addFragment(
            fragment: Fragment,
            modifyData: (T) -> Unit,
            performSearch: (String) -> LiveData<T>,
            aType: String
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_frame, fragment)
            .addToBackStack(fragment.tag) // hack to prevent fragment onDestroy being called
            .commit()
        val title = searchEditText.text.toString()
        performSearch(title).observe(this, {
            Log.d(LOG_TAG, "$aType response was: $it")
            modifyData(it)
        })
    }

    private val seasonReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(LOG_TAG, "intent? $intent")
            if (intent?.action == Constants.SEASON_BROADCAST) {
                intent.getSerializableExtra(Constants.SEASON_DATA)?.let {
                    it as SeasonData
                    seasonViewModel.getSeasonResult(it.series, it.season).observe(
                            this@MainActivity,
                            { u ->
                                Log.d(LOG_TAG, "how often is this called?")
                                supportFragmentManager
                                        .beginTransaction()
                                        .replace(R.id.main_frame, seasonFragment)
//                                        .addToBackStack(seasonFragment.tag)
                                        .commitNow()
                                Log.d(LOG_TAG, "${it.season} response was: $u")
                                seasonFragment.updateSeason(u)

                            })



                }
            }
        }
    }



    override fun onStart() {
        super.onStart()
        registerReceiver(seasonReceiver, IntentFilter(Constants.SEASON_BROADCAST))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(seasonReceiver)
    }
}