package com.example.dvoitekh.fitnesstracker.models

import com.example.dvoitekh.fitnesstracker.startOfDay
import java.util.*

class Day(val id: Long = 1, date: Long = startOfDay(System.currentTimeMillis()),
          val steps: Long = 0, val calories: Float = 0f, val distance: Float = 0f,
          val longitude: Float = 0f, val latitude: Float = 0f, val address: String = "") {

    var date: Date

    init {
        this.date = Date(date)
    }
}