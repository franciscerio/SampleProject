package com.exam.domain.poko

data class Trip(
    override val name: String,
    val startTime: String,
    val stopTime: String,
    val distance: Double
) : Driver(name)