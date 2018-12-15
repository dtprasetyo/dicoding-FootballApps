package com.odora.id.footballapps.ui.detail.team.player

import com.google.gson.Gson
import com.odora.id.footballapps.api.ApiRepository
import com.odora.id.footballapps.api.TheSportDBApi
import com.odora.id.footballapps.model.PlayerResponse
import com.odora.id.footballapps.util.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerPresenter(private val view: PlayerView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val contextPool: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getListPlayer(teamId: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main){
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportDBApi.getPlayer(teamId)).await(),
                    PlayerResponse::class.java)

            view.getPlayer(data.player)
            view.hideLoading()
        }
    }
}