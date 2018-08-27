package com.example.prasetyo.footballapps.event

import com.example.prasetyo.footballapps.TestContextProvider
import com.example.prasetyo.footballapps.api.ApiRepository
import com.example.prasetyo.footballapps.api.TheSportDBApi
import com.example.prasetyo.footballapps.model.*
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class EventPresenterTest {

    @Mock
    private
    lateinit var view: EventView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getLeagueList() {
        val leagues: MutableList<Leagues> = mutableListOf()
        val response = LeagueResponse(leagues)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.allLeagues()),
                LeagueResponse::class.java
        )).thenReturn(response)

        presenter.getLeagueList()

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showLeagueList(leagues)
        Mockito.verify(view).hideLoading()

    }

    @Test
    fun getLastEvents() {
        val events: MutableList<Events> = mutableListOf()
        val response = EventResponse(events)
        val id = "4346"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.lastEvents(id)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getLastEvents(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).getTeams(events)
        Mockito.verify(view).hideLoading()

        Thread.sleep(4000)
    }

    @Test
    fun getNextEvents() {
        val events: MutableList<Events> = mutableListOf()
        val response = EventResponse(events)
        val id = "4346"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.nextEvents(id)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getNextEvents(id)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).getTeams(events)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun searchEvent() {
        val event: MutableList<Events> = mutableListOf()
        val response = SearchEventResponse(event)
        val name = "Arsenal"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.searchEvent(name)),
                SearchEventResponse::class.java
        )).thenReturn(response)

        presenter.getLastEvents(name)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).getTeams(event)
        Mockito.verify(view).hideLoading()

    }
}