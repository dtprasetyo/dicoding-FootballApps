package com.example.prasetyo.footballapps.detail.match

import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.api.TheSportDBApi
import com.example.prasetyo.footballapps.model.EventResponse
import com.example.prasetyo.footballapps.model.MatchDetailResponse
import com.example.prasetyo.footballapps.model.TeamResponse
import com.example.prasetyo.footballapps.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getMatchDetails(id: String) {
        view.showLoading()

        async(contextPool.main){

            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.matchDetails(id)),
                        MatchDetailResponse::class.java
                )

            }

            view.showMatchDetail(data.await().events)

            view.hideLoading()

        }
    }

    fun getEvents(id: String) {
        view.showLoading()

        async(contextPool.main){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.matchDetails(id)),
                        EventResponse::class.java
                )
            }

            view.showEvent(data.await().events)
            view.hideLoading()
        }
    }

    fun getHomeTeamBadge(id: String) {
        view.showLoading()

        async(contextPool.main){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.teamDetaild(id)),
                        TeamResponse::class.java
                )
            }

            view.showHomeTeamDetail(data.await().teams)
            view.hideLoading()
        }
    }

    fun getAwayTeamBadge(id: String) {
        view.showLoading()

        async(contextPool.main){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.teamDetaild(id)),
                        TeamResponse::class.java
                )
            }

            view.showAwayTeamDetail(data.await().teams)
            view.hideLoading()
        }
    }

}