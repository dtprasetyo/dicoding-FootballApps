package com.example.prasetyo.footballapps.detail.team.player.detail

import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.api.TheSportDBApi
import com.example.prasetyo.footballapps.model.PlayerDetailResponse
import com.example.prasetyo.footballapps.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerDetailPresenter(private val view: PlayerDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getPlayerDetails(id: String) {
        view.showLoading()

        async(contextPool.main) {

            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.playerDetail(id)),
                        PlayerDetailResponse::class.java
                )

            }

            view.showPlayerDetail(data.await().players)

            view.hideLoading()

        }
    }
}