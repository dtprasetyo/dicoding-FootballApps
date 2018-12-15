package com.odora.id.footballapps.ui.event.search

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import com.google.gson.Gson
import com.odora.id.footballapps.R
import com.odora.id.footballapps.api.ApiRepository
import com.odora.id.footballapps.model.Events
import com.odora.id.footballapps.model.Leagues
import com.odora.id.footballapps.ui.event.EventPresenter
import com.odora.id.footballapps.ui.event.EventView
import com.odora.id.footballapps.ui.main.MainActivity
import com.odora.id.footballapps.util.invisible
import com.odora.id.footballapps.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class SearchMatchActivity : AppCompatActivity(), EventView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private lateinit var presenter: EventPresenter

    private var events: MutableList<Events> = mutableListOf()
    private var layoutManager: LinearLayoutManager? = null

    private lateinit var adapter: SearchAdapter

    private lateinit var mainUI: SearchMatchActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainUI = SearchMatchActivityUI()
        mainUI.setContentView(this)
        val request = ApiRepository()
        val gson = Gson()
        presenter = EventPresenter(this, request, gson)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showLeagueList(data: List<Leagues>) {

    }

    override fun hideLoading() {
        mainUI.progressBar.invisible()
    }

    override fun showLoading() {
        mainUI.progressBar.visible()
    }


    override fun showSnackbar(message: String) {
        snackbar(mainUI.swipeRefresh, message).show()
    }

    override fun getTeams(data: List<Events>) {
        mainUI.swipeRefresh.isRefreshing = false
        events.clear()
        events.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        mainUI.listTeam.layoutManager = layoutManager
        mainUI.listTeam.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        events.clear()
        adapter = SearchAdapter(events)
        mainUI.listTeam.adapter = adapter
        adapter.notifyDataSetChanged()
        mainUI.swipeRefresh.isRefreshing = false
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_view, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.isIconified = false
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Telusuri"
        return super.onCreateOptionsMenu(menu)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity<MainActivity>()
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                startActivity<MainActivity>()
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        presenter.searchEvent(newText)
        initRecyclerView()
        return false
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        return true
    }

    class SearchMatchActivityUI : AnkoComponent<SearchMatchActivity> {

        lateinit var listTeam: RecyclerView
        lateinit var progressBar: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout

        lateinit var spinner: Spinner

        override fun createView(ui: AnkoContext<SearchMatchActivity>) = with(ui) {

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(R.color.colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        listTeam = recyclerView {
                            id = R.id.listTeam
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx)
                        }

                        progressBar = progressBar { }.lparams {
                            centerHorizontally()
                        }
                    }
                }

            }
        }

    }
}
