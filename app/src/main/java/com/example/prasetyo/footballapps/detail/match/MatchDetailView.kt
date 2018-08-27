package com.example.prasetyo.footballapps.detail.match

import com.example.prasetyo.footballapps.model.Events
import com.example.prasetyo.footballapps.model.MatchDetails
import com.example.prasetyo.footballapps.model.Teams

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<MatchDetails>)
    fun showHomeTeamDetail(data: List<Teams>)
    fun showAwayTeamDetail(data: List<Teams>)
    fun showEvent(data: List<Events>)
}