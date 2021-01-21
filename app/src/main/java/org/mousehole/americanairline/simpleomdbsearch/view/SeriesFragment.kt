package org.mousehole.americanairline.simpleomdbsearch.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.mousehole.americanairline.simpleomdbsearch.R
import org.mousehole.americanairline.simpleomdbsearch.model.series.SeriesResponse
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.LOG_TAG
import org.mousehole.americanairline.simpleomdbsearch.view.adapter.SeriesSeasonItemAdapter

class SeriesFragment : Fragment() {

    private lateinit var posterImageView : ImageView
    private lateinit var seriesTitleTextView : TextView
    private lateinit var numberOfSeasonsTextView : TextView
    private lateinit var synopsisTextView: TextView

    private lateinit var seasonRecyclerView : RecyclerView

    private val seasonItemAdapter = SeriesSeasonItemAdapter("", listOf())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.series_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.let { v ->
            posterImageView = v.findViewById(R.id.poster_imageview)
            seriesTitleTextView = v.findViewById(R.id.series_title_textview)
            numberOfSeasonsTextView = v.findViewById(R.id.number_seasons_textview)
            synopsisTextView = v.findViewById(R.id.synopsis_textview)

            seasonRecyclerView = v.findViewById(R.id.season_recylerview)
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(seasonRecyclerView)
            seasonRecyclerView.adapter = seasonItemAdapter
        }

    }

    fun changeSeries(seriesResponse: SeriesResponse) {
        seriesResponse.let {
            seriesTitleTextView.text = it.Title
            numberOfSeasonsTextView.text = "Total Season: ${it.totalSeasons}"
            synopsisTextView.text = it.Plot
            val seasonList = try {
                1.rangeTo(Integer.parseInt(it.totalSeasons))
            } catch (e : Exception) {
                Log.d(LOG_TAG, "totalSeasons was not an integer? ${it.totalSeasons}")
                listOf(1)
            }
            seasonItemAdapter.setSeasonList(seriesResponse.Title, seasonList.toList())
            seasonRecyclerView.scrollToPosition(0)
            Glide.with(this).load(seriesResponse.Poster).into(posterImageView)

        }
    }

}