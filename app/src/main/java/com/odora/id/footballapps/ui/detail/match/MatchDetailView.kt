package com.odora.id.footballapps.ui.detail.match

import com.odora.id.footballapps.model.Events
import com.odora.id.footballapps.model.MatchDetails
import com.odora.id.footballapps.model.Teams

interface MatchDetailView {
    fun showLoading()
    fun hideLoading()
    fun showMatchDetail(data: List<MatchDetails>)
    fun showHomeTeamDetail(data: List<Teams>)
    fun showAwayTeamDetail(data: List<Teams>)
    fun showEvent(data: List<Events>)
}