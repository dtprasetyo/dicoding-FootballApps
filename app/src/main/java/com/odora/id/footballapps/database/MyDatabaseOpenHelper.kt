package com.odora.id.footballapps.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.odora.id.footballapps.model.FavoriteMatches
import com.odora.id.footballapps.model.FavoriteTeams
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(ctx: Context) :ManagedSQLiteOpenHelper(ctx, "FavoritesTeam.db", null, 1) {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteMatches.TABLE_FAVORITE, true,
                FavoriteMatches.ID to TEXT + PRIMARY_KEY,
                FavoriteMatches.HOME_TEAM_ID to TEXT,
                FavoriteMatches.AWAY_TEAM_ID to TEXT,
                FavoriteMatches.HOME_TEAM_NAME to TEXT,
                FavoriteMatches.AWAY_TEAM_NAME to TEXT,
                FavoriteMatches.HOME_TEAM_SCORE to TEXT,
                FavoriteMatches.AWAY_TEAM_SCORE to TEXT,
                FavoriteMatches.DATE to TEXT)

        db.createTable(FavoriteTeams.TABLE_FAVORITE, true,
                FavoriteTeams.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeams.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeams.TEAM_NAME to TEXT,
                FavoriteTeams.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteMatches.TABLE_FAVORITE, true)
        db.dropTable(FavoriteTeams.TABLE_FAVORITE, true)
    }
}

val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)