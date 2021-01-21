package org.mousehole.americanairline.simpleomdbsearch.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import org.mousehole.americanairline.simpleomdbsearch.R

class SeriesSeasonItemAdapter(private var seasonList: List<Int>) : RecyclerView.Adapter<SeriesSeasonItemAdapter.SeasonItemViewHolder>() {
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
                Log.d("TAG_X","Should switch to Season View for season $season")
            }
        }
    }

    override fun getItemCount(): Int =
        seasonList.size

    fun setSeasonList(season : List<Int>) {
        seasonList = season
        Log.d("TAG_X", seasonList.toString())
        notifyDataSetChanged()
    }

}