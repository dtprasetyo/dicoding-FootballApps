package com.example.prasetyo.footballapps.detail.team.player

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.prasetyo.footballapps.R
import com.example.prasetyo.footballapps.detail.team.player.detail.PlayerDetailActivity
import com.example.prasetyo.footballapps.model.Players
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayerAdapter(private val players: List<Players>)
    : RecyclerView.Adapter<PlayerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position])
    }

    override fun getItemCount(): Int = players.size

}

class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL
                id = R.id.rootView
                weightSum = 5f

                imageView {
                    id = R.id.player_thumb
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = R.id.player_name
                }.lparams(width= matchParent, height = wrapContent){
                    margin = dip(15)
                    weight = 2f
                }

                textView {
                    id = R.id.player_position
                }.lparams(width= matchParent, height = wrapContent){
                    margin = dip(15)
                    weight = 2f
                }

            }
        }
    }

}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private val playerThumb: ImageView = view.find(R.id.player_thumb)
    private val playerName: TextView = view.find(R.id.player_name)
    private val playerPosition: TextView = view.find(R.id.player_position)
    private val rootView: LinearLayout = itemView.rootView as LinearLayout

    fun bindItem(players: Players) {
        Picasso.get().load(players.strThumb).into(playerThumb)
        playerName.text = players.strPlayer
        playerPosition.text = players.strPosition
        rootView.onClick {
           itemView.context.startActivity<PlayerDetailActivity>("playerId" to "${players.idPlayer}")
        }
    }
}