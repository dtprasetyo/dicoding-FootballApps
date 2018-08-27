package com.example.prasetyo.footballapps.detail.team.overview

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.prasetyo.footballapps.R.color.colorAccent
import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.model.Teams
import com.example.prasetyo.footballapps.util.invisible
import com.example.prasetyo.footballapps.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamOverviewFragment : Fragment(), TeamOverviewView {
    private lateinit var presenter: TeamOverviewPresenter
    private lateinit var teams: Teams
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private lateinit var teamDescription: TextView

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var teamId: String

    private lateinit var mainUI: TeamOverviewFragmentUI

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamOverviewPresenter(this, request, gson)

        teamId= getArguments()?.getString("id").toString();

        mainUI = TeamOverviewFragmentUI()

        return mainUI.createView(AnkoContext.create(ctx, this)).apply {

            presenter.getTeamDetail(teamId)

            mainUI.swipeRefresh.onRefresh {
                presenter.getTeamDetail(teamId)
            }

        }

    }

    fun newInstance(id: String): TeamOverviewFragment {
        val fragment = TeamOverviewFragment()
        val args = Bundle()
        args.putString("id", id)
        fragment.setArguments(args)
        return fragment
    }

    override fun showLoading() {
        mainUI.progressBar.visible()
    }

    override fun hideLoading() {
        mainUI.progressBar.invisible()
    }

    override fun showTeamDetail(data: List<Teams>) {
        teams = Teams(data[0].teamId,
                data[0].teamName,
                data[0].teamBadge)
        mainUI.swipeRefresh.isRefreshing = false
        mainUI.teamDescription.text = data[0].teamDescription

    }

    class TeamOverviewFragmentUI : AnkoComponent<TeamOverviewFragment> {

        lateinit var swipeRefresh: SwipeRefreshLayout
        lateinit var progressBar: ProgressBar
        lateinit var teamDescription: TextView

        override fun createView(ui: AnkoContext<TeamOverviewFragment>) = with(ui) {

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL
                backgroundColor = Color.WHITE

                swipeRefresh = swipeRefreshLayout {
                    setColorSchemeResources(colorAccent,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light,
                            android.R.color.holo_red_light)

                    scrollView {
                        isVerticalScrollBarEnabled = false
                        relativeLayout {
                            lparams(width = matchParent, height = wrapContent)

                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                padding = dip(10)
                                orientation = LinearLayout.VERTICAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                padding = dip(10)

                                teamDescription = textView().lparams {
                                    topMargin = dip(20)
                                    bottomMargin = dip(20)
                                }
                            }
                            progressBar = progressBar {
                            }.lparams {
                                centerHorizontally()
                            }
                        }
                    }
                }
            }

        }
    }
}