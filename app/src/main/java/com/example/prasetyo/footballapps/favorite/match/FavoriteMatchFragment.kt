package com.example.prasetyo.footballapps.favorite.match


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
import com.example.prasetyo.footballapps.database.database
import com.example.prasetyo.footballapps.model.FavoriteMatches
import com.example.prasetyo.footballapps.util.invisible
import com.example.prasetyo.footballapps.util.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import java.util.*

class FavoriteMatchFragment : Fragment() {

    private val itemList = ArrayList<FavoriteMatches>()
    private lateinit var adapter: FavoriteMatchAdapter
    private var layoutManager: LinearLayoutManager? = null

    private lateinit var mainUI: FavoriteFragmentUI

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mainUI = FavoriteFragmentUI()
        return mainUI.createView(AnkoContext.create(ctx, this)).apply {
            itemList.clear()
            showLoading()
            initRecyclerView()
            getMatch()

            mainUI.swipeRefresh.onRefresh {
                mainUI.swipeRefresh.isRefreshing = false
                itemList.clear()
                adapter.notifyDataSetChanged()
                showLoading()
                initRecyclerView()
                getMatch()
            }

        }
    }

    private fun getMatch() {

        context?.database?.use {
            mainUI.swipeRefresh.isRefreshing = false
            val result = select(FavoriteMatches.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatches>())
            itemList.addAll(favorite)
            adapter.notifyDataSetChanged()
            mainUI.swipeRefresh.isRefreshing = false
            hideLoading()
        }

    }


    private fun hideLoading() {
        mainUI.progressBar.invisible()
    }

    private fun showLoading() {
        mainUI.progressBar.visible()
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(context)
        mainUI.listTeam.layoutManager = layoutManager
        mainUI.listTeam.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        itemList.clear()
        adapter = FavoriteMatchAdapter(itemList)
        mainUI.listTeam.setAdapter(adapter)
        adapter.notifyDataSetChanged()
        mainUI.swipeRefresh.setRefreshing(false)
    }

    class FavoriteFragmentUI : AnkoComponent<FavoriteMatchFragment> {

        lateinit var listTeam: RecyclerView
        lateinit var progressBar: ProgressBar
        lateinit var swipeRefresh: SwipeRefreshLayout

        override fun createView(ui: AnkoContext<FavoriteMatchFragment>) = with(ui) {

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
