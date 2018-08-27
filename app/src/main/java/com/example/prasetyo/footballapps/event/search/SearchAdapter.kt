package com.example.prasetyo.footballapps.event.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.prasetyo.footballapps.R
import com.example.prasetyo.footballapps.detail.match.MatchDetailActivity
import com.example.prasetyo.footballapps.model.Events
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class SearchAdapter(private val events: List<Events>) : RecyclerView.Adapter<SearchAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {

        return TeamsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.match, parent, false))
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(events [position])
    }

    override fun getItemCount(): Int = events.size

    class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txDate: TextView = itemView.find(R.id.txDate)
        private val txHomeTeam: TextView = itemView.find(R.id.txHomeTeam)
        private val txAwayTeam: TextView = itemView.find(R.id.txAwayTeam)
        private val txAwayScore: TextView = itemView.find(R.id.txAwayScore)
        private val txHomeScore: TextView = itemView.find(R.id.txHomeScore)
        private val rootView: LinearLayout = itemView.rootView as LinearLayout

        fun bindItem(events: Events) {
            if(!events.strDate.equals("null")) {
                txDate.text = events.strDate
            }
            if(!events.homeTeam.equals("null")) {
                txHomeTeam.text = events.homeTeam
            }
            if(!events.awayTeam.equals("null")) {
                txAwayTeam.text = events.awayTeam
            }
            if(!events.homeScore.toString().equals("null")) {
                txHomeScore.text = events.homeScore.toString()
            }
            if(!events.awayScore.toString().equals("null")) {
                txAwayScore.text = events.awayScore.toString()
            }

            rootView.onClick {
                itemView.context.startActivity<MatchDetailActivity>("idHomeTeam" to "${events.idHomeTeam}", "idAwayTeam" to "${events.idAwayTeam}", "idEvent" to "${events.idEvent}", "fragment" to "past")
            }

        }
    }

}