package com.example.prasetyo.footballapps.detail.team.overview

import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.api.TheSportDBApi
import com.example.prasetyo.footballapps.model.TeamResponse
import com.example.prasetyo.footballapps.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamOverviewPresenter(private val view: TeamOverviewView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        async(contextPool.main){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.teamDetaild(teamId)),
                        TeamResponse::class.java
                )
            }

            view.showTeamDetail(data.await().teams)
            view.hideLoading()
        }
    }
}