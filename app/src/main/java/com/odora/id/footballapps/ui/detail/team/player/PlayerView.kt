package com.odora.id.footballapps.ui.detail.team.player

import com.odora.id.footballapps.model.Players

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun getPlayer(data: List<Players>)
}