package org.mousehole.americanairline.simpleomdbsearch.view.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import org.mousehole.americanairline.simpleomdbsearch.R
import org.mousehole.americanairline.simpleomdbsearch.model.SeasonData
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.LOG_TAG
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.SEASON_BROADCAST
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.SEASON_DATA

class SeriesSeasonItemAdapter(private var series : String, private var seasonList: List<Int>) : RecyclerView.Adapter<SeriesSeasonItemAdapter.SeasonItemViewHolder>() {
    class SeasonItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val seasonNumber : Button = itemView.findViewById(R.id.season_number_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeasonItemViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.season_number_layout, parent, false)
        return SeasonItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeasonItemViewHolder, position: Int) {
        val season = seasonList[position]
        holder.apply {
            seasonNumber.text = "Season $season"
            seasonNumber.setOnClickListener {
                Log.d(LOG_TAG,"Should switch to Season View for season $season")
                seasonNumber.context?.let {
                    it.sendBroadcast(Intent(SEASON_BROADCAST).also { t ->
                        t.putExtra(SEASON_DATA, SeasonData(series, season.toString()))
                    })
                }
            }
        }
    }

    override fun getItemCount(): Int =
        seasonList.size

    fun setSeasonList(series : String, season : List<Int>) {
        this.series = series
        seasonList = season
        Log.d(LOG_TAG, seasonList.toString())
        notifyDataSetChanged()
    }

}