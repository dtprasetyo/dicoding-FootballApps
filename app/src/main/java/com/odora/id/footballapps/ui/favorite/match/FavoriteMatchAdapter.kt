package com.odora.id.footballapps.ui.favorite.match

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.odora.id.footballapps.R
import com.odora.id.footballapps.model.FavoriteMatches
import com.odora.id.footballapps.ui.detail.match.MatchDetailActivity
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class FavoriteMatchAdapter(private val favorites: List<FavoriteMatches>) : RecyclerView.Adapter<FavoriteMatchAdapter.TeamsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {

        return TeamsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.match, parent, false))
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(favorites [position])
    }

    override fun getItemCount(): Int = favorites.size

    class TeamsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var fragment: String

        private val txDate: TextView = itemView.find(R.id.txDate)
        private val txHomeTeam: TextView = itemView.find(R.id.txHomeTeam)
        private val txAwayTeam: TextView = itemView.find(R.id.txAwayTeam)
        private val txAwayScore: TextView = itemView.find(R.id.txAwayScore)
        private val txHomeScore: TextView = itemView.find(R.id.txHomeScore)
        private val rootView: LinearLayout = itemView.rootView as LinearLayout

        fun bindItem(favorites: FavoriteMatches) {
            txDate.text = favorites.strDate
            txHomeTeam.text = favorites.homeTeam
            txAwayTeam.text = favorites.awayTeam
            if(!favorites.homeScore.toString().equals("null") && !favorites.awayScore.toString().equals("null")) {
                txHomeScore.text = favorites.homeScore.toString()
                txAwayScore.text = favorites.awayScore.toString()
                fragment = "past"
            }else{
                fragment = "next"
            }

            rootView.setOnClickListener {
                itemView.context.startActivity<MatchDetailActivity>("idHomeTeam" to "${favorites.idHomeTeam}", "idAwayTeam" to "${favorites.idAwayTeam}", "idEvent" to "${favorites.idEvent}", "fragment" to "${fragment}")
            }

        }
    }

}