package com.example.prasetyo.footballapps.detail.team.overview

import com.example.prasetyo.footballapps.model.Teams

interface TeamOverviewView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<Teams>)
}