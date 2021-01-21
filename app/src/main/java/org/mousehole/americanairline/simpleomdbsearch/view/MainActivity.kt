package org.mousehole.americanairline.simpleomdbsearch.view

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import org.mousehole.americanairline.simpleomdbsearch.R
import org.mousehole.americanairline.simpleomdbsearch.viewmodel.MovieViewModel
import org.mousehole.americanairline.simpleomdbsearch.viewmodel.SeriesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var searchEditText : EditText
    private lateinit var mainFrameLayout: FrameLayout
    private lateinit var searchButton : Button
    private lateinit var typeRadioGroup : RadioGroup


    private val seriesFragment: SeriesFragment = SeriesFragment()
    private val seriesViewModel: SeriesViewModel by viewModels()

    private val movieFragment: MovieFragment = MovieFragment()
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        typeRadioGroup = findViewById(R.id.type_radiogroup)

        searchEditText = findViewById(R.id.search_textinput)

        searchButton = findViewById(R.id.search_button)

        searchButton.setOnClickListener {
            when (typeRadioGroup.checkedRadioButtonId) {
                R.id.movie_radiobutton -> {
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.main_frame, movieFragment)
                            .addToBackStack(movieFragment.tag)
                            .commit()
                    val title = searchEditText.text.toString()
                    movieViewModel.getMovieResult(title).observe(this, {
                        Log.d("TAG_X", "movie response was: $it")
                        movieFragment.changeMovie(it)
                    })
                }

                else -> {
                    supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.main_frame, seriesFragment)
                            .addToBackStack(seriesFragment.tag)
                            .commit()
                    val title = searchEditText.text.toString()
                    seriesViewModel.getSeriesResult(title).observe(this, {
                        Log.d("TAG_X", "series response was: $it")
                        seriesFragment.changeSeries(it)
                    })
                }
            }
        }

        mainFrameLayout = findViewById(R.id.main_frame)



    }
}