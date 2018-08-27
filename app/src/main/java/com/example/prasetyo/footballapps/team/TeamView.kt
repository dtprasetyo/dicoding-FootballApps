package com.example.prasetyo.footballapps.team

import com.example.prasetyo.footballapps.model.Leagues
import com.example.prasetyo.footballapps.model.Teams

interface TeamView {
        fun showLoading()
        fun hideLoading()
        fun showTeamList(data: List<Teams>)
        fun showLeagueList(data: List<Leagues>)

}