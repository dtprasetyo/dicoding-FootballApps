package com.example.prasetyo.footballapps.event

import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.api.TheSportDBApi
import com.example.prasetyo.footballapps.model.EventResponse
import com.example.prasetyo.footballapps.model.LeagueResponse
import com.example.prasetyo.footballapps.model.SearchEventResponse
import com.example.prasetyo.footballapps.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class EventPresenter(private val view: EventView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getLeagueList() {
        view.showLoading()

        async(contextPool.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.allLeagues()),
                        LeagueResponse::class.java
                )
            }
            view.showLeagueList(data.await().leagues)
            view.hideLoading()
        }
    }

    fun getLastEvents(id: String) {
        view.showLoading()

        async(contextPool.main) {

            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.lastEvents(id)),
                        EventResponse::class.java
                )

            }

            view.getTeams(data.await().events)

            view.hideLoading()

        }
    }

    fun getNextEvents(id: String) {
        view.showLoading()

        async(contextPool.main) {

            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.nextEvents(id)),
                        EventResponse::class.java
                )

            }

            view.getTeams(data.await().events)

            view.hideLoading()
        }.invokeOnCompletion { e ->

        }


    }

    fun searchEvent(eventName: String?) {
        view.showLoading()

        async(contextPool.main) {

            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.searchEvent(eventName)),
                        SearchEventResponse::class.java
                )

            }

            view.getTeams(data.await().event)

            view.hideLoading()
        }


    }
}

