package com.example.prasetyo.footballapps.detail.team.player

import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.api.TheSportDBApi
import com.example.prasetyo.footballapps.model.PlayerResponse
import com.example.prasetyo.footballapps.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayerView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getListPlayer(teamId: String) {
        view.showLoading()

        async(contextPool.main){
            val data = bg{
                gson.fromJson(apiRepository
                        .doRequest(TheSportDBApi.getPlayer(teamId)),
                        PlayerResponse::class.java
                )
            }

            view.getPlayer(data.await().player)
            view.hideLoading()
        }
    }
}