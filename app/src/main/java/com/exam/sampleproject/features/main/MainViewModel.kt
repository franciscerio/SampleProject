package com.exam.sampleproject.features.main

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.exam.domain.poko.Driver
import com.exam.domain.poko.Trip
import com.exam.sampleproject.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import com.exam.domain.poko.Output
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.time.hours
import android.R.attr.duration
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _state by lazy {
        PublishSubject.create<MainState>()
    }

    val state: Observable<MainState> = _state

    private val _listOfDrivers by lazy {
        MutableLiveData<MutableList<Driver>>().apply {
            postValue(mutableListOf())
        }
    }

    val listOfDrivers: LiveData<MutableList<Driver>> = _listOfDrivers

    private val outputItems: MutableList<Output> = mutableListOf()

    override fun isFirstTimeUiCreate(bundle: Bundle?) = Unit

    // Check driver's if exists
    fun saveOrIgnoreDriverData(name: String) {
        val driver = Driver(name)
        // First item will be added to listOfDrivers
        if (_listOfDrivers.value?.isNullOrEmpty()!!) {
            addAndUpdate(driver)
            _state.onNext(MainState.AddItem(driver))
            return
        }

        val driverNotExist =
            _listOfDrivers.value?.singleOrNull { it.name.contains(name, ignoreCase = true) }

        if (driverNotExist == null) {
            addAndUpdate(driver)
            _state.onNext(MainState.AddItem(driver))
        } else {
            _state.onNext(MainState.DriverAlreadyExist(name))
        }
    }

    // Save driver trip data
    fun saveOrIgnoreDriverTripData(trip: Trip) {
        addAndUpdate(trip)
        _state.onNext(MainState.AddItem(trip))
    }

    fun invalidTimeFormat(position: Int) {
        _state.onNext(MainState.InvalidTime(position))
    }

    fun invalidStartTime() {
        _state.onNext(MainState.StartTimeGreaterThanStopTime)
    }

    // Generate report
    fun calculateItems() {
        outputItems.clear()

        val items = listOfDrivers.value.orEmpty()

        if (items.isEmpty()) {
            _state.onNext(MainState.EmptyList)
            return
        }

        items.forEachIndexed { _, driver ->
            val isTrip = driver is Trip
            val formatter = DateTimeFormatter.ofPattern("HH:mm")

            // if empty, add automatically
            if (outputItems.isEmpty()) {
                outputItems.add(Output(driver.name, 0.0, 0.0))
                return@forEachIndexed
            }

            // if user is on trip
            if (isTrip) {

                // Add all output items
                val item = driver as Trip

                val startDateTime = LocalTime.parse(item.startTime, formatter)
                val stopDateTime = LocalTime.parse(item.stopTime, formatter)

                val minutes = Duration.between(startDateTime, stopDateTime).toMinutes()

                outputItems.forEachIndexed output@{ _, output ->
                    // if user is found inside outputItems
                    if (driver.name.contains(output.name, ignoreCase = true)) {
                        output.minutes += minutes
                        output.miles += item.distance
                    }
                }

            }
            // If user is driver
            else {
                val userExist = outputItems.singleOrNull {
                    it.name.contains(driver.name, ignoreCase = true)
                }

                if (userExist == null) {
                    outputItems.add(Output(driver.name, 0.0, 0.0))
                }
            }

        }

        // compute mph
        outputItems.forEachIndexed { _, output ->
            val mph: Int = if (output.miles > 0 && output.minutes > 0) {
                (output.miles / output.minutes * 60).roundToInt()
            } else {
                0
            }
            output.mph = mph
        }

        // filter report, exclude if mph is less than 5 and more than 100, sort it
        _state.onNext(MainState.ExpectedOutput(outputItems.filterNot {
            return@filterNot it.mph < 5 || it.mph > 100
        }.toMutableList()
            .apply {
                sortByDescending { it.miles }
            }
        ))
    }

    // Add and update items
    fun addAndUpdate(driver: Driver) {
        with(_listOfDrivers) {
            value?.add(driver)
            postValue(value)
        }
    }
}