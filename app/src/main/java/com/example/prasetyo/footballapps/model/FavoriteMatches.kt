package com.example.prasetyo.footballapps.model

data class FavoriteMatches(

        var idEvent: String? = null,

        var idHomeTeam: String? = null,

        var idAwayTeam: String? = null,

        var homeTeam: String? = null,

        var awayTeam: String? = null,

        var homeScore: String? = null,

        var awayScore: String? = null,

        var strDate: String? = null) {

    companion object {

        const val TABLE_FAVORITE: String = "TABLE_MATCH_FAVORITE"
        const val ID: String = "ID_"
        const val HOME_TEAM_NAME: String = "HOME_TEAM_NAME"
        const val AWAY_TEAM_NAME: String = "AWAY_TEAM_NAME"
        const val HOME_TEAM_ID: String = "HOME_TEAM_ID"
        const val AWAY_TEAM_ID: String = "AWAY_TEAM_ID"
        const val HOME_TEAM_SCORE: String = "HOME_TEAM_SCORE"
        const val AWAY_TEAM_SCORE: String = "AWAY_TEAM_SCORE"
        const val DATE: String = "DATE"

    }
}
