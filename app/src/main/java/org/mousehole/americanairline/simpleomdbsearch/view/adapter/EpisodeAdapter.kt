package org.mousehole.americanairline.simpleomdbsearch.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.mousehole.americanairline.simpleomdbsearch.R
import org.mousehole.americanairline.simpleomdbsearch.model.season.Episode
import org.mousehole.americanairline.simpleomdbsearch.model.season.SeasonResponse

class EpisodeAdapter(private var season:SeasonResponse) : RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    class EpisodeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val episodeTitleTextView : TextView = itemView.findViewById(R.id.episode_title_textview)
        val episodeSeasonEpisodeTextView : TextView = itemView.findViewById(R.id.episode_season_episode_textview)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.episode_item_layout, parent, false)
        return EpisodeViewHolder(view)

    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = season.Episodes[position]
        holder.apply {
            episodeTitleTextView.text = episode.Title
            episodeSeasonEpisodeTextView.text = "Seasons ${season.Season} Episode ${episode.Episode}"
        }
    }

    override fun getItemCount(): Int = season.Episodes.size
}