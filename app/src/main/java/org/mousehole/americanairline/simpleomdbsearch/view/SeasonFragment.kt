package org.mousehole.americanairline.simpleomdbsearch.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import org.mousehole.americanairline.simpleomdbsearch.R
import org.mousehole.americanairline.simpleomdbsearch.model.SeasonData
import org.mousehole.americanairline.simpleomdbsearch.model.season.Episode
import org.mousehole.americanairline.simpleomdbsearch.model.season.SeasonResponse
import org.mousehole.americanairline.simpleomdbsearch.util.Constants
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.SEASON_DATA
import org.mousehole.americanairline.simpleomdbsearch.view.adapter.EpisodeAdapter

class SeasonFragment : Fragment() {

    private lateinit var seasonTitleTextView : TextView
    private lateinit var episodeRecyclerView : RecyclerView

    val episodeAdapter = EpisodeAdapter(SeasonResponse(listOf(), "", "", "", ""))

    private val SeasonReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if(intent?.action == Constants.SEASON_BROADCAST) {
                intent.getParcelableExtra<SeasonData>(SEASON_DATA)?.let{

                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.season_fragment_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        episodeRecyclerView = view.findViewById(R.id.episode_recyclerview)
        episodeRecyclerView.adapter = episodeAdapter


    }

    override fun onStart() {
        super.onStart()
    }
}