package com.example.dvoitekh.fitnesstracker

import com.example.dvoitekh.fitnesstracker.helpers.MyDatabaseOpenHelper
import com.example.dvoitekh.fitnesstracker.models.Day
import org.jetbrains.anko.db.*
import java.util.*

fun startOfDay(timestamp : Long): Long {
    val cal = Calendar.getInstance()
    cal.timeInMillis = timestamp
    cal.set(Calendar.HOUR_OF_DAY, 0) //set hours to zero
    cal.set(Calendar.MINUTE, 0) // set minutes to zero
    cal.set(Calendar.SECOND, 0) //set seconds to zero
    cal.set(Calendar.MILLISECOND, 0) //set seconds to zero
    return cal.timeInMillis
}

fun getOrCreateToday(database : MyDatabaseOpenHelper): Day {
    val candidates = database.use {
        select("Day")
                    .whereArgs("date = {startOfDay}",
                        "startOfDay" to startOfDay(System.currentTimeMillis()))
                    .limit(1)
                    .parseList(classParser<Day>()
                )
    }

    return if (candidates.count() != 0) {
        candidates[0]
    } else {
        val today = Day()
        database.use {
            insert("Day",
                    "date" to startOfDay(System.currentTimeMillis()),
                    "steps" to today.steps,
                    "calories" to today.calories,
                    "distance" to today.distance,
                    "longitude" to today.longitude,
                    "latitude" to today.latitude,
                    "address" to today.address
            )
        }
        today
    }
}