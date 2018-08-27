package com.example.prasetyo.footballapps.detail.team

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.prasetyo.footballapps.R
import com.example.prasetyo.footballapps.R.drawable.ic_add_to_favorites
import com.example.prasetyo.footballapps.R.drawable.ic_added_to_favorites
import com.example.prasetyo.footballapps.R.id.add_to_favorite
import com.example.prasetyo.footballapps.R.menu.detail_menu
import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.database.database
import com.example.prasetyo.footballapps.detail.team.overview.TeamOverviewFragment
import com.example.prasetyo.footballapps.detail.team.overview.TeamOverviewPresenter
import com.example.prasetyo.footballapps.detail.team.overview.TeamOverviewView
import com.example.prasetyo.footballapps.detail.team.player.PlayerFragment
import com.example.prasetyo.footballapps.model.FavoriteTeams
import com.example.prasetyo.footballapps.model.Teams
import com.example.prasetyo.footballapps.util.invisible
import com.example.prasetyo.footballapps.util.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class TeamDetailActivity : AppCompatActivity(), TeamOverviewView {

    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var id: String
    var int_items = 2

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var presenter: TeamOverviewPresenter
    private lateinit var teams: Teams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        swipeRefresh = findViewById(R.id.container)

        val intent = intent
        id = intent.getStringExtra("id")
        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamOverviewPresenter(this, request, gson)

        presenter.getTeamDetail(id)
        viewpager.adapter = MyAdapter(supportFragmentManager);
        tabs.post{ tabs.setupWithViewPager(viewpager) }


    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeams.TABLE_FAVORITE)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeams>())
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
                insert(FavoriteTeams.TABLE_FAVORITE,
                        FavoriteTeams.TEAM_ID to teams.teamId,
                        FavoriteTeams.TEAM_NAME to teams.teamName,
                        FavoriteTeams.TEAM_BADGE to teams.teamBadge)
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteTeams.TABLE_FAVORITE, "(TEAM_ID = {id})",
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

    override fun showLoading() {
        pDialog.visible()
    }

    override fun hideLoading() {
        pDialog.invisible()
    }

    override fun showTeamDetail(data: List<Teams>) {
        teams = Teams(data[0].teamId,
                data[0].teamName,
                data[0].teamBadge)
        teamBadge.setImageUrl(teams.teamBadge)
        teamName.text = data[0].teamName
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium

    }


    internal inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        /**
         * Return fragment with respect to Position .
         */

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return TeamOverviewFragment().newInstance(id)
                1 -> return PlayerFragment().newInstance(id)
            }
            return TeamOverviewFragment().newInstance(id)
        }

        override fun getCount(): Int {

            return int_items

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        override fun getPageTitle(position: Int): CharSequence? {

            when (position) {
                0 -> return "Overview"
                1 -> return "Players"
            }
            return null
        }
    }
}
