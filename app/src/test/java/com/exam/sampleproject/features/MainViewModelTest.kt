package com.exam.sampleproject.features

import androidx.lifecycle.Observer
import com.exam.sampleproject.core.TestSchedulerProvider
import com.exam.sampleproject.features.main.MainState
import com.exam.sampleproject.features.main.MainViewModel
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.exam.domain.poko.Driver
import org.junit.Rule
import org.junit.rules.TestRule


class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private val testScheduler = TestScheduler()
    private val schedulers = TestSchedulerProvider(testScheduler)

    private val observer = mock(TestObserver::class.java) as TestObserver<MainState>

    private val liveDataObserver: Observer<MainState> =
        mock(Observer::class.java) as Observer<MainState>

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        viewModel = MainViewModel()
        viewModel.schedulers = schedulers
        viewModel.state.subscribe(observer)
    }

    @Test
    fun `when driver added to list and doesnt exist`() {
        val name = "Francis"
        val expected = "Francis"

        viewModel.saveOrIgnoreDriverData(name)
        testScheduler.triggerActions()

        ArgumentCaptor.forClass(MainState.AddItem::class.java).run {
            verify(observer, times(1)).onNext(capture())
            Assert.assertEquals(expected, value.item.name)
        }
    }

    @Test
    fun `when driver is already exists`() {
        val name = "Francis"
        val exist = "Francis"

        viewModel.addAndUpdate(Driver(name))

        viewModel.saveOrIgnoreDriverData(name)
        testScheduler.triggerActions()

        ArgumentCaptor.forClass(MainState.DriverAlreadyExist::class.java).run {
            verify(observer, times(1)).onNext(capture())
            Assert.assertEquals(MainState.DriverAlreadyExist(exist), value)
        }
    }

}