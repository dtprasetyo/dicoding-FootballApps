package com.odora.id.footballapps.ui.detail.team.overview

import com.google.gson.Gson
import com.odora.id.footballapps.api.ApiRepository
import com.odora.id.footballapps.api.TheSportDBApi
import com.odora.id.footballapps.model.TeamResponse
import com.odora.id.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamOverviewPresenter(private val view: TeamOverviewView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.teamDetaild(teamId)).await(),
                    TeamResponse::class.java)

            view.showTeamDetail(data.teams)
            view.hideLoading()
        }
    }
}