package org.mousehole.americanairline.simpleomdbsearch.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.mousehole.americanairline.simpleomdbsearch.R
import org.mousehole.americanairline.simpleomdbsearch.model.season.SeasonResponse
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.LOG_TAG
import org.mousehole.americanairline.simpleomdbsearch.view.adapter.EpisodeAdapter

class SeasonFragment : Fragment() {

    private lateinit var seasonTitleTextView : TextView
    private lateinit var episodeRecyclerView : RecyclerView

    private val episodeAdapter = EpisodeAdapter(SeasonResponse(listOf(), "", "", "", ""))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOG_TAG, "onCreateView Is this even running? ")
        return inflater.inflate(R.layout.season_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(LOG_TAG, "onViewCreated This view was created, right?")
        seasonTitleTextView = view.findViewById(R.id.series_and_season_textview)
        Log.d(LOG_TAG, "onViewCreated id is? ${R.id.series_and_season_textview}")

        episodeRecyclerView = view.findViewById(R.id.episode_recyclerview)
        episodeRecyclerView.adapter = episodeAdapter
    }

    fun updateSeason(seasonResponse : SeasonResponse) {
        Log.d(LOG_TAG, "updateSeason")
        seasonTitleTextView.text = seasonResponse.Title
        episodeAdapter.updateSeasonResponse(seasonResponse)
    }

}