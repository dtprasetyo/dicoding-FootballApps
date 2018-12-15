package com.odora.id.footballapps.ui.team

import com.odora.id.footballapps.model.Leagues
import com.odora.id.footballapps.model.Teams

interface TeamView {
        fun showLoading()
        fun hideLoading()
        fun showTeamList(data: List<Teams>)
        fun showLeagueList(data: List<Leagues>)

}