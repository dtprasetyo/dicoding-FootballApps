package com.example.prasetyo.footballapps.detail.team.player.detail

import com.example.prasetyo.footballapps.model.Players

interface PlayerDetailView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerDetail(data: List<Players>)

}