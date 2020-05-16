package com.exam.sampleproject.features.main

import com.exam.domain.poko.Driver
import com.exam.domain.poko.Output
import com.exam.domain.poko.Trip

sealed class MainState {

    data class DriverAlreadyExist(val name: String) : MainState()

    data class AddItem(val item: Driver) : MainState()

    data class UpdateList(val items: List<Driver>) : MainState()

    data class InvalidTime(val position: Int) : MainState()

    data class ExpectedOutput(val items: List<Output>): MainState()

    object StartTimeGreaterThanStopTime : MainState()

    object EmptyList : MainState()

}