package com.odora.id.footballapps.ui.detail.team.player.detail

import com.google.gson.Gson
import com.odora.id.footballapps.api.ApiRepository
import com.odora.id.footballapps.api.TheSportDBApi
import com.odora.id.footballapps.model.PlayerDetailResponse
import com.odora.id.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerDetailPresenter(private val view: PlayerDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getPlayerDetails(id: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.playerDetail(id)).await(),
                    PlayerDetailResponse::class.java)

            view.showPlayerDetail(data.players)
            view.hideLoading()
        }
    }
}