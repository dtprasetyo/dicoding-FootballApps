package com.example.prasetyo.footballapps.team

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.example.prasetyo.footballapps.R
import com.example.prasetyo.footballapps.R.color.colorAccent
import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.detail.team.TeamDetailActivity
import com.example.prasetyo.footballapps.model.Leagues
import com.example.prasetyo.footballapps.model.Teams
import com.example.prasetyo.footballapps.util.invisible
import com.example.prasetyo.footballapps.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class TeamFragment : Fragment(), AnkoComponent<Context>, TeamView, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener  {


    private var teams: MutableList<Teams> = mutableListOf()
    private var leagues: MutableList<Leagues> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var spinAdapter: SpinnerAdapter
    private lateinit var spinner: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String

    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        setHasOptionsMenu(true)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TeamAdapter(teams) {
            ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
        }

        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)
        presenter.getLeagueList()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                leagueName = leagues.get(position).strLeague?.replace(" ","%20").toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        swipeRefresh.onRefresh {
            presenter.getLeagueList()
            presenter.getTeamList(leagueName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.listEvent
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Teams>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showLeagueList(data: List<Leagues>) {

        leagues.clear()
        leagues.addAll(data)

        spinAdapter = SpinnerAdapter(android.R.layout.simple_spinner_dropdown_item, leagues)
        spinner.adapter = spinAdapter

        leagueName = leagues.get(0).strLeague?.replace(" ","%20").toString()
        presenter.getTeamList(leagueName)

    }

    internal inner class SpinnerAdapter(textViewResourceId: Int,
            private val values: List<Leagues>) : ArrayAdapter<Leagues>(context, textViewResourceId, values) {

        override fun getCount(): Int {
            return values.size
        }

        override fun getItem(position: Int): Leagues? {
            return values[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val label = super.getView(position, convertView, parent) as TextView
            label.setText(values[position].strLeague)

            return label
        }

        override fun getDropDownView(position: Int, convertView: View?,
                                     parent: ViewGroup): View {
            val label = super.getDropDownView(position, convertView, parent) as TextView
            label.setText(values[position].strLeague)

            return label
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_view, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Telusuri"

        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        presenter.searchTeam(newText)
        return false
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        return true
    }

}