package com.odora.id.footballapps.ui.event.pastEvent


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.odora.id.footballapps.R
import com.odora.id.footballapps.api.ApiRepository
import com.odora.id.footballapps.model.Events
import com.odora.id.footballapps.model.Leagues
import com.odora.id.footballapps.ui.event.EventPresenter
import com.odora.id.footballapps.ui.event.EventView
import com.odora.id.footballapps.util.invisible
import com.odora.id.footballapps.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PreviousMatchFragment : Fragment(), EventView {


    private lateinit var presenter: EventPresenter

    private var events: MutableList<Events> = mutableListOf()
    private var leagues: MutableList<Leagues> = mutableListOf()
    private lateinit var spinAdapter: SpinnerAdapter
    private var layoutManager: LinearLayoutManager? = null
    private lateinit var idLeague: String

    private lateinit var adapter: PastEventAdapter

    private lateinit var mainUI: PreviousMatchFragmentUI

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val request = ApiRepository()
        val gson = Gson()
        presenter = EventPresenter(this, request, gson)

        mainUI = PreviousMatchFragmentUI()
        return mainUI.createView(AnkoContext.create(ctx, this)).apply {
            presenter.getLeagueList()

            mainUI.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    idLeague = leagues.get(position).idLeague.toString()
                    presenter.getLastEvents(idLeague)
                    initRecyclerView()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

            mainUI.swipeRefresh.onRefresh {
                presenter.getLastEvents(idLeague)
                initRecyclerView()
            }

        }

    }

    override fun showLeagueList(data: List<Leagues>) {

        leagues.clear()
        leagues.addAll(data)


        spinAdapter = com.odora.id.footballapps.ui.event.SpinnerAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagues)
        mainUI.spinner.adapter = spinAdapter

        idLeague = leagues.get(0).idLeague.toString()
        presenter.getLastEvents(idLeague)

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
        layoutManager = LinearLayoutManager(context)
        mainUI.listTeam.layoutManager = layoutManager
        mainUI.listTeam.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        events.clear()
        adapter = PastEventAdapter(events)
        mainUI.listTeam.adapter = adapter
        adapter.notifyDataSetChanged()
        mainUI.swipeRefresh.isRefreshing = false
    }

    class PreviousMatchFragmentUI : AnkoComponent<PreviousMatchFragment> {

        lateinit var listTeam: RecyclerView
        lateinit var progressBar: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout

        lateinit var spinner: Spinner

        override fun createView(ui: AnkoContext<PreviousMatchFragment>) = with(ui) {

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL


                spinner = spinner {
                    id = R.id.spinner
                }

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
