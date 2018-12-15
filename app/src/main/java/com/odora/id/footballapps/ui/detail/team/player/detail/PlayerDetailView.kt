package com.odora.id.footballapps.ui.detail.team.player.detail

import com.odora.id.footballapps.model.Players

interface PlayerDetailView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerDetail(data: List<Players>)

}