package com.example.prasetyo.footballapps.favorite

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.prasetyo.footballapps.R
import com.example.prasetyo.footballapps.favorite.match.FavoriteMatchFragment
import com.example.prasetyo.footballapps.favorite.team.FavoriteTeamsFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.viewPager

class FavoriteFragment : Fragment() {

    private lateinit var mainUI: FavoriteFragmentUI
    var int_items = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        mainUI = FavoriteFragment.FavoriteFragmentUI()
        return mainUI.createView(AnkoContext.create(ctx, this)).apply {

            mainUI.viewPager.adapter = MyAdapter(childFragmentManager);
            mainUI.tabs.post { mainUI.tabs.setupWithViewPager(mainUI.viewPager) }
        }

    }

    internal inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        /**
         * Return fragment with respect to Position .
         */

        override fun getItem(position: Int): Fragment? {
            when (position) {
                0 -> return FavoriteTeamsFragment()
                1 -> return FavoriteMatchFragment()
            }
            return null
        }

        override fun getCount(): Int {

            return int_items

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        override fun getPageTitle(position: Int): CharSequence? {

            when (position) {
                0 -> return "Fav Team"
                1 -> return "Fav Match"
            }
            return null
        }
    }

    class FavoriteFragmentUI : AnkoComponent<FavoriteFragment> {

        lateinit var tabs: TabLayout
        lateinit var viewPager: ViewPager

        override fun createView(ui: AnkoContext<FavoriteFragment>) = with(ui) {

            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL


                tabs = tabLayout {
                    backgroundResource = R.color.colorPrimary
                    id = R.id.tabs
                    tabGravity = Gravity.FILL
                    tabMode = TabLayout.MODE_FIXED
                    tabTextColors = ContextCompat.getColorStateList(context, R.color.white)
                    setSelectedTabIndicatorColor(ContextCompat.getColor(context, R.color.white))
                }.lparams(width = matchParent, height = wrapContent)


                viewPager = viewPager{
                    id = R.id.viewPager
                }.lparams(matchParent, matchParent)

            }

        }
    }

}