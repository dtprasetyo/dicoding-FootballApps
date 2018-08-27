package com.example.prasetyo.footballapps.event

import com.example.prasetyo.footballapps.model.Events
import com.example.prasetyo.footballapps.model.Leagues

interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showSnackbar(message: String)
    fun getTeams(data: List<Events>)
    fun showLeagueList(data: List<Leagues>)
}