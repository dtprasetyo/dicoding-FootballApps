package com.example.prasetyo.footballapps.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.prasetyo.footballapps.R
import com.example.prasetyo.footballapps.event.MatchFragment
import com.example.prasetyo.footballapps.favorite.FavoriteFragment
import com.example.prasetyo.footballapps.team.TeamFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.bottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mainUI: MainActivityUI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainUI = MainActivityUI()
        mainUI.setContentView(this)

        loadFragment(TeamFragment())

        mainUI.bottomNavigationView.setOnNavigationItemSelectedListener (this)

    }


    private fun loadFragment(fragment: Fragment?): Boolean {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null

        when (item.itemId) {
            R.id.nav_match -> fragment = MatchFragment()
            R.id.nav_team -> fragment = TeamFragment()
            R.id.nav_favorite -> fragment = FavoriteFragment()
        }

        return loadFragment(fragment)

    }


    class MainActivityUI : AnkoComponent<MainActivity> {
        lateinit var bottomNavigationView: BottomNavigationView

        @SuppressLint("ResourceType")
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {

            relativeLayout{
                lparams(width = matchParent, height = wrapContent)

                frameLayout{
                    id = R.id.container
                }.lparams(width = matchParent, height = matchParent){
                    above(R.id.bottom_navigation)
                }

                view {
                    backgroundResource = R.drawable.shadow
                }.lparams(width = matchParent, height = dip(4)){
                    alignParentBottom()
                }

                bottomNavigationView = bottomNavigationView{
                    id = R.id.bottom_navigation
                    fitsSystemWindows = true
                    inflateMenu(R.menu.navigation)
                    backgroundResource = R.color.white
                    itemTextColor = ContextCompat.getColorStateList(context , R.drawable.nav_item_color_state)
                    itemIconTintList = ContextCompat.getColorStateList(context , R.drawable.nav_item_color_state)

                }.lparams(width = matchParent, height = wrapContent){
                    alignParentBottom()
                }
            }
        }
    }
}
