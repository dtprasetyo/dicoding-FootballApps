package com.example.prasetyo.footballapps.detail.team.player

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.example.prasetyo.footballapps.R
import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.model.Players
import com.example.prasetyo.footballapps.util.invisible
import com.example.prasetyo.footballapps.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerFragment : Fragment(), PlayerView{

    private lateinit var presenter: PlayerPresenter

    private var players: MutableList<Players> = mutableListOf()
    private lateinit var adapter: PlayerAdapter
    private var layoutManager: LinearLayoutManager? = null

    private lateinit var mainUI: PlayerFragmentUI

    private lateinit var teamId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        teamId= getArguments()?.getString("id").toString();

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)

        mainUI = PlayerFragmentUI()
        return mainUI.createView(AnkoContext.create(ctx, this)).apply {
            showLoading()
            presenter.getListPlayer(teamId)
            initRecyclerView()

            mainUI.swipeRefresh.onRefresh {
                presenter.getListPlayer(teamId)
                initRecyclerView()
            }

        }

    }

    fun newInstance(id: String): PlayerFragment {
        val fragment = PlayerFragment()
        val args = Bundle()
        args.putString("id", id)
        fragment.setArguments(args)
        return fragment
    }


    override fun hideLoading() {
        mainUI.progressBar.invisible()
    }

    override fun showLoading() {
        mainUI.progressBar.visible()
    }

    override fun getPlayer(data: List<Players>) {
        mainUI.swipeRefresh.isRefreshing = false
        players.clear()
        players.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        mainUI.listPlayer.layoutManager = layoutManager
        mainUI.listPlayer.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        players.clear()
        adapter = PlayerAdapter(players)
        mainUI.listPlayer.adapter = adapter
        adapter.notifyDataSetChanged()
        mainUI.swipeRefresh.isRefreshing = false
    }

    class PlayerFragmentUI : AnkoComponent<PlayerFragment> {

        lateinit var listPlayer: RecyclerView
        lateinit var progressBar: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout

        override fun createView(ui: AnkoContext<PlayerFragment>) = with(ui) {

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

                        listPlayer = recyclerView {
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