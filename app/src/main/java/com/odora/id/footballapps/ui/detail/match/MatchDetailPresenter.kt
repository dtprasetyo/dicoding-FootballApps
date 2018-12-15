package com.odora.id.footballapps.ui.detail.match

import com.google.gson.Gson
import com.odora.id.footballapps.api.ApiRepository
import com.odora.id.footballapps.api.TheSportDBApi
import com.odora.id.footballapps.model.EventResponse
import com.odora.id.footballapps.model.MatchDetailResponse
import com.odora.id.footballapps.model.TeamResponse
import com.odora.id.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getMatchDetails(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.matchDetails(id)).await(),
                    MatchDetailResponse::class.java)

            view.showMatchDetail(data.events)
            view.hideLoading()
        }
    }

    fun getEvents(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.matchDetails(id)).await(),
                    EventResponse::class.java)

            view.showEvent(data.events)
            view.hideLoading()
        }
    }

    fun getHomeTeamBadge(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.teamDetaild(id)).await(),
                    TeamResponse::class.java)

            view.showHomeTeamDetail(data.teams)
            view.hideLoading()
        }
    }

    fun getAwayTeamBadge(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.teamDetaild(id)).await(),
                    TeamResponse::class.java)

            view.showAwayTeamDetail(data.teams)
            view.hideLoading()
        }
    }

}