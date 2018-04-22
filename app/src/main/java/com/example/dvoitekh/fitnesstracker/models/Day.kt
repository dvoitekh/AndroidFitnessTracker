package com.example.dvoitekh.fitnesstracker.models

import java.util.*

class Day(val id: Long, date: Long, val steps: Long, val calories: Float, val distance: Float) {

    var date: Date

    init {
        this.date = Date(date)
    }
}