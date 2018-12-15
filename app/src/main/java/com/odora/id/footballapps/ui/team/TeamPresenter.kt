package com.odora.id.footballapps.ui.team

import com.google.gson.Gson
import com.odora.id.footballapps.api.ApiRepository
import com.odora.id.footballapps.api.TheSportDBApi
import com.odora.id.footballapps.model.LeagueResponse
import com.odora.id.footballapps.model.TeamResponse
import com.odora.id.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamsPresenter(private val view: TeamView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(league: String?) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getTeams(league)).await(),
                    TeamResponse::class.java)

            view.showTeamList(data.teams)
            view.hideLoading()
        }
    }

    fun searchTeam(teamName: String?) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.searchTeam(teamName)).await(),
                    TeamResponse::class.java)

            view.showTeamList(data.teams)
            view.hideLoading()
        }
    }

    fun getLeagueList() {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.allLeagues()).await(),
                    LeagueResponse::class.java)

            view.showLeagueList(data.leagues)
            view.hideLoading()
        }
    }
}