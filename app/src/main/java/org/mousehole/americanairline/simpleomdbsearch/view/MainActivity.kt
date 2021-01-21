package org.mousehole.americanairline.simpleomdbsearch.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
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
        }

        mainFrameLayout = findViewById(R.id.main_frame)
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
            if (intent?.action == Constants.SEASON_BROADCAST) {
                intent.getSerializableExtra(Constants.SEASON_DATA)?.let {
                    it as SeasonData
                    seasonViewModel.getSeasonResult(it.series, it.season).observe(
                            this@MainActivity,
                            { _ ->
                                Log.d(LOG_TAG, "how often is this called?")
                                supportFragmentManager
                                        .beginTransaction()
                                        .replace(R.id.main_frame, seasonFragment)
                                        .commitNow()
                                seasonViewModel.getSeasonResult(it.series, it.season).observe(this@MainActivity,
                                        { u ->
                                            Log.d(LOG_TAG, "${it.season} response was: $u")
                                            seasonFragment.updateSeason(u)
                                        })
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