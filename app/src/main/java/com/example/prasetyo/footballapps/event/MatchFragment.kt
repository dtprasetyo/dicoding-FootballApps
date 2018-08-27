package com.example.prasetyo.footballapps.event

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.LinearLayout
import com.example.prasetyo.footballapps.R
import com.example.prasetyo.footballapps.event.nextEvent.NextMatchFragment
import com.example.prasetyo.footballapps.event.pastEvent.PreviousMatchFragment
import com.example.prasetyo.footballapps.event.search.SearchMatchActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.viewPager


class MatchFragment : Fragment(), SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener   {

    private lateinit var mainUI: MatchFragmentUI
    var int_items = 2
    private var mContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity
        setHasOptionsMenu(true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        mainUI = MatchFragment.MatchFragmentUI()
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
                0 -> return PreviousMatchFragment()
                1 -> return NextMatchFragment()
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
                0 -> return "Last Match"
                1 -> return "Next Match"
            }
            return null
        }
    }

    class MatchFragmentUI : AnkoComponent<MatchFragment> {

        lateinit var tabs: TabLayout
        lateinit var viewPager: ViewPager

        override fun createView(ui: AnkoContext<MatchFragment>) = with(ui) {

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
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_view, menu)
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = "Telusuri"

        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === R.id.search) {
            ctx.startActivity<SearchMatchActivity>()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
        return true
    }


}