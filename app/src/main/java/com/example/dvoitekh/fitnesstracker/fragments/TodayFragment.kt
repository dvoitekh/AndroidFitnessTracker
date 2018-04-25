package com.example.dvoitekh.fitnesstracker.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dvoitekh.fitnesstracker.R
import com.example.dvoitekh.fitnesstracker.helpers.MyDatabaseOpenHelper
import com.example.dvoitekh.fitnesstracker.models.Day
import kotlinx.android.synthetic.main.fragment_today.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select


class TodayFragment : Fragment() {
    companion object {
        fun newInstance(): TodayFragment {
            val fragmentToday = TodayFragment()
            val args = Bundle()
            fragmentToday.arguments = args
            return fragmentToday
        }
    }

    private lateinit var database: MyDatabaseOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = MyDatabaseOpenHelper.getInstance(context)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val t = object : Thread() {
            override fun run() = try {
                while (!isInterrupted) {
                    Thread.sleep(500)
                    activity?.runOnUiThread({
                        val today = database.use {
                            select("Day")
                                    .whereArgs("id = {dayId}", "dayId" to 15)
                                    .parseList(classParser<Day>())[0]
                        }
                        stepsTxt?.text = today.steps.toString()
                        distanceTxt?.text = "%.2f".format(today.distance)
                        caloriesTxt?.text = "%.2f".format(today.calories)
                        locationTxt?.text = "Longitude: " + today.longitude + "\n" + "Latitude: " + today.latitude + "\n" + "Address: " + today.address
                    })
                }
            } catch (e: Exception) {
            }
        }
        t.start()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_today, container, false)
    }
}
