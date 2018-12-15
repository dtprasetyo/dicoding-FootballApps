package com.odora.id.footballapps.ui.event

import com.odora.id.footballapps.model.Events
import com.odora.id.footballapps.model.Leagues

interface EventView {
    fun showLoading()
    fun hideLoading()
    fun showSnackbar(message: String)
    fun getTeams(data: List<Events>)
    fun showLeagueList(data: List<Leagues>)
}