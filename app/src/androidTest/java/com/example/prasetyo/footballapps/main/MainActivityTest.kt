package com.example.prasetyo.footballapps.main

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.example.prasetyo.footballapps.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        Espresso.onView(ViewMatchers.withId(spinner))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(4000)
        Espresso.onView(ViewMatchers.withId(listEvent))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(listEvent)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        Espresso.onView(ViewMatchers.withId(listEvent)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, ViewActions.click()))
        Espresso.pressBack()
        Thread.sleep(4000)
        Espresso.onView(ViewMatchers.withId(spinner)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("German Bundesliga")).perform(ViewActions.click())
        Thread.sleep(7000)

        Espresso.onView(ViewMatchers.withText("Hamburg"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Hamburg")).perform(ViewActions.click())
        Thread.sleep(4000)

        Espresso.onView(ViewMatchers.withId(add_to_favorite))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(add_to_favorite)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Added to favorite"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.pressBack()

        Espresso.onView(ViewMatchers.withId(bottom_navigation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(nav_favorite)).perform(ViewActions.click())
    }
}