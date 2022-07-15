package com.example.virginapitest.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.example.virginapitest.common.Results
import com.example.virginapitest.domain.PeopleRepository
import com.example.virginapitest.domain.entity.People
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PeopleViewModelTest {

    val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Mock
    lateinit var peopleRepository: PeopleRepository

    private lateinit var viewModel: PeopleViewModel


    @Before
    fun doBefore() {
        Dispatchers.setMain(dispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = PeopleViewModel(peopleRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun loadPerson_success() = runBlockingTest {
        val person = listOf<People>(
            People(
                24,
                "Ada",
                "Cayla63@yahoo.com"
            ),
            People(
                11,
                "Ally",
                "Alex.Waelchi9@gmail.com"
            ),
        )

        whenever(peopleRepository.getPeople()).thenReturn(Results.Ok(person))
        viewModel.submitAction(PeopleItemAction.FetchPerson)
        viewModel.peopleEvent.asLiveData().observeForever() {
            assertEquals((it as PeopleEvent.PersonLoaded).data, person)
            assertEquals((it as PeopleEvent.PersonLoaded).data.count(), 2)
        }
    }

    @Test
    fun loadPeople_fail() = runBlockingTest {
        whenever(peopleRepository.getPeople()).thenReturn(Results.Error(RuntimeException("Api failed")))
        viewModel.submitAction(PeopleItemAction.FetchPerson)
        viewModel.peopleEvent.asLiveData().observeForever() {
            assertEquals(it.javaClass, PeopleEvent.PersonError.javaClass)

        }
    }
}