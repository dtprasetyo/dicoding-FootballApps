package com.odora.id.footballapps.ui.detail.team.overview

import com.odora.id.footballapps.model.Teams

interface TeamOverviewView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Teams>)
}