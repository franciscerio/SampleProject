package com.exam.sampleproject.ext

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun DateTimeFormatter.validate(dateStr: String): Boolean {
    try {
        this.parse(dateStr)
    } catch (e: DateTimeParseException) {
        return false
    }

    return true
}