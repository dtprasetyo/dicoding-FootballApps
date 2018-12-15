package com.odora.id.footballapps.api

import com.odora.id.footballapps.BuildConfig

object TheSportDBApi {
    fun nextEvents(id: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventsnextleague.php?id=" + id
    }

    fun lastEvents(id: String?): String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/eventspastleague.php?id=" + id
    }

    fun allLeagues(): String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/all_leagues.php"
    }

    fun teamDetaild(id: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupteam.php?id=" + id
    }

    fun matchDetails(id: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupevent.php?id=" + id
    }

    fun getTeams(league: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/search_all_teams.php?l=" + league
    }

    fun getPlayer(id: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookup_all_players.php?id=" + id
    }

    fun playerDetail(playerId: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/lookupplayer.php?id=" + playerId
    }

    fun searchTeam(teamName: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchteams.php?t=" + teamName
    }

    fun searchEvent(eventName: String?): String {
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}" + "/searchevents.php?e=" + eventName
    }
}