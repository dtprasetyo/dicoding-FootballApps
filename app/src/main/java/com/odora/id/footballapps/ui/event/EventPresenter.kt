package com.odora.id.footballapps.ui.event

import com.google.gson.Gson
import com.odora.id.footballapps.api.ApiRepository
import com.odora.id.footballapps.api.TheSportDBApi
import com.odora.id.footballapps.model.EventResponse
import com.odora.id.footballapps.model.LeagueResponse
import com.odora.id.footballapps.model.SearchEventResponse
import com.odora.id.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

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

    fun getLastEvents(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.lastEvents(id)).await(),
                    EventResponse::class.java)

            view.getTeams(data.events)
            view.hideLoading()
        }

    }

    fun getNextEvents(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.nextEvents(id)).await(),
                    EventResponse::class.java)

            view.getTeams(data.events)
            view.hideLoading()
        }

    }

    fun searchEvent(eventName: String?) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.searchEvent(eventName)).await(),
                    SearchEventResponse::class.java)

            view.getTeams(data.event)
            view.hideLoading()
        }

    }
}

