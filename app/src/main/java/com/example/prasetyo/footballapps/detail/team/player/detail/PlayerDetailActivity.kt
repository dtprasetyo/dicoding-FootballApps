package com.example.prasetyo.footballapps.detail.team.player.detail

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.prasetyo.footballapps.R
import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.model.Players
import com.example.prasetyo.footballapps.util.invisible
import com.example.prasetyo.footballapps.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_player_detail.*
import org.jetbrains.anko.support.v4.onRefresh

class PlayerDetailActivity : AppCompatActivity(), PlayerDetailView{

    private lateinit var presenter: PlayerDetailPresenter
    private lateinit var player: Players

    private lateinit var playerId: String

    private var menuItem: Menu? = null

    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        supportActionBar?.title = "Player Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        playerId = intent.getStringExtra("playerId")

        swipeRefresh = findViewById(R.id.swipeRefresh)

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerDetailPresenter(this, request, gson)
        presenter.getPlayerDetails(playerId)
        swipeRefresh.onRefresh {

            presenter.getPlayerDetails(playerId)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        pDialog.visible()
    }

    override fun hideLoading() {
        pDialog.invisible()
    }

    override fun showPlayerDetail(data: List<Players>) {

        player = Players(data[0].idPlayer,
                data[0].strPlayer,
                data[0].dateBorn,
                data[0].strDescriptionEN,
                data[0].strPosition,
                data[0].strFanart1,
                data[0].strThumb)

        swipeRefresh.isRefreshing = false
        player_fanart.setImageUrl(data[0].strFanart1)
        playerName.text = data[0].strPlayer
        dateBorn.text = data[0].dateBorn
        playerPosition.text = data[0].strPosition
        playerDescription.text = data[0].strDescriptionEN
    }

}