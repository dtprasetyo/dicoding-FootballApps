package com.odora.id.footballapps.ui.detail.match

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.odora.id.footballapps.R
import com.odora.id.footballapps.R.drawable.ic_add_to_favorites
import com.odora.id.footballapps.R.drawable.ic_added_to_favorites
import com.odora.id.footballapps.R.id.add_to_favorite
import com.odora.id.footballapps.R.menu.detail_menu
import com.odora.id.footballapps.api.ApiRepository
import com.odora.id.footballapps.database.database
import com.odora.id.footballapps.model.Events
import com.odora.id.footballapps.model.FavoriteMatches
import com.odora.id.footballapps.model.MatchDetails
import com.odora.id.footballapps.model.Teams
import com.odora.id.footballapps.util.invisible
import com.odora.id.footballapps.util.visible
import kotlinx.android.synthetic.main.activity_match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh

class MatchDetailActivity : AppCompatActivity(), MatchDetailView {

    private lateinit var presenter: MatchDetailPresenter

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var events: Events
    private lateinit var matchDetails: MatchDetails
    private lateinit var teams: Teams

    private lateinit var idHomeTeam: String
    private lateinit var idAwayTeam: String
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        swipeRefresh = findViewById(R.id.container)

        idHomeTeam = intent.getStringExtra("idHomeTeam")
        idAwayTeam = intent.getStringExtra("idAwayTeam")
        id = intent.getStringExtra("idEvent")

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = MatchDetailPresenter(this, request, gson)
        presenter.getAwayTeamBadge(idAwayTeam)
        presenter.getHomeTeamBadge(idHomeTeam)
        presenter.getEvents(id)
        presenter.getMatchDetails(id)
        swipeRefresh.onRefresh {

            presenter.getMatchDetails(id)
        }

    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteMatches.TABLE_FAVORITE)
                    .whereArgs("(ID_ = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteMatches>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                if(intent.getStringExtra("fragment").equals("past")) {
                    insert(FavoriteMatches.TABLE_FAVORITE,
                            FavoriteMatches.ID to id,
                            FavoriteMatches.HOME_TEAM_ID to idHomeTeam,
                            FavoriteMatches.AWAY_TEAM_ID to idAwayTeam,
                            FavoriteMatches.HOME_TEAM_NAME to txHomeTeam.text.toString(),
                            FavoriteMatches.AWAY_TEAM_NAME to txAwayTeam.text.toString(),
                            FavoriteMatches.DATE to txDate.text.toString(),
                            FavoriteMatches.HOME_TEAM_SCORE to txHomeScore.text.toString(),
                            FavoriteMatches.AWAY_TEAM_SCORE to txAwayScore.text.toString(),
                            FavoriteMatches.DATE to txDate.text.toString())
                }else{
                    insert(FavoriteMatches.TABLE_FAVORITE,
                            FavoriteMatches.ID to id,
                            FavoriteMatches.HOME_TEAM_ID to idHomeTeam,
                            FavoriteMatches.AWAY_TEAM_ID to idAwayTeam,
                            FavoriteMatches.HOME_TEAM_NAME to txHomeTeam.text.toString(),
                            FavoriteMatches.AWAY_TEAM_NAME to txAwayTeam.text.toString(),
                            FavoriteMatches.DATE to txDate.text.toString())
                }
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteMatches.TABLE_FAVORITE, "(ID_ = {id})",
                        "id" to id)
            }
            snackbar(swipeRefresh, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    override fun hideLoading() {
        pDialog.invisible()
    }

    override fun showLoading() {
        pDialog.visible()
    }

    override fun showEvent(data: List<Events>) {

        events = Events(data[0].homeTeam,
                data[0].awayTeam,
                data[0].strDate,
                data[0].idHomeTeam,
                data[0].idAwayTeam,
                data[0].homeScore,
                data[0].awayScore,
                data[0].idEvent)

        swipeRefresh.isRefreshing = false
        txDate.text = data[0].strDate
        txHomeTeam.text = data[0].homeTeam
        txAwayTeam.text = data[0].awayTeam
        if(intent.getStringExtra("fragment").equals("past")) {
            txHomeScore.text = data[0].homeScore.toString()
            txAwayScore.text = data[0].awayScore.toString()
        }

    }

    override fun showHomeTeamDetail(data: List<Teams>) {
        teams = Teams(data[0].teamId,
                data[0].teamBadge)

        swipeRefresh.isRefreshing = false
        homeBadge.setImageUrl(data[0].teamBadge)


    }

    override fun showAwayTeamDetail(data: List<Teams>) {
        teams = Teams(data[0].teamId,
                data[0].teamBadge)

        swipeRefresh.isRefreshing = false
        awayBadge.setImageUrl(data[0].teamBadge)


    }

    override fun showMatchDetail(data: List<MatchDetails>) {
        matchDetails = MatchDetails(data[0].strHomeGoalDetails,
                data[0].strHomeRedCard,
                data[0].strHomeYellowCards,
                data[0].strHomeLineupGoalkeeper,
                data[0].strHomeLineupDefense,
                data[0].strHomeLineupMidfield,
                data[0].strHomeLineupForward,
                data[0].strHomeLineupSubstitutes,
                data[0].strAwayGoalDetails,
                data[0].strAwayRedCard,
                data[0].strAwayYellowCards,
                data[0].strAwayLineupGoalkeeper,
                data[0].strAwayLineupDefense,
                data[0].strAwayLineupMidfield,
                data[0].strAwayLineupForward,
                data[0].strAwayLineupSubstitutes
                )

        swipeRefresh.isRefreshing = false
        if(intent.getStringExtra("fragment").equals("past")) {
            txHomeDefense.text = data[0].strHomeLineupDefense
            txHomeGoalKeeper.text = data[0].strHomeLineupGoalkeeper
            txHomeMidfield.text = data[0].strHomeLineupMidfield
            txHomeForward.text = data[0].strHomeLineupForward
            txHomeGoals.text = data[0].strHomeGoalDetails
            txHomeSubtitutes.text = data[0].strAwayGoalDetails
            txHomeYellowCard.text = data[0].strHomeYellowCards
            txHomeRedCard.text = data[0].strHomeRedCard
            txAwayDefense.text = data[0].strAwayLineupDefense
            txAwayGoalKeeper.text = data[0].strAwayLineupGoalkeeper
            txAwayMidfield.text = data[0].strAwayLineupMidfield
            txAwayForward.text = data[0].strAwayLineupForward
            txAwayGoals.text = data[0].strAwayGoalDetails
            txAwaySubtitutes.text = data[0].strAwayGoalDetails
            txAwayYellowCards.text = data[0].strAwayYellowCards
            txAwayRedCards.text = data[0].strAwayRedCard
        }
    }

}
