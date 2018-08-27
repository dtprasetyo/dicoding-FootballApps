package com.example.prasetyo.footballapps.detail.team.player

import com.example.prasetyo.footballapps.model.Players

interface PlayerView {
    fun showLoading()
    fun hideLoading()
    fun getPlayer(data: List<Players>)
}