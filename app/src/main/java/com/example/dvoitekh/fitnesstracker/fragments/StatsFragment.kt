package com.example.dvoitekh.fitnesstracker.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.example.dvoitekh.fitnesstracker.R
import com.example.dvoitekh.fitnesstracker.adapters.DaysAdapter
import com.example.dvoitekh.fitnesstracker.models.Day
import java.util.*
import kotlinx.android.synthetic.main.fragment_stats.*


class StatsFragment : Fragment() {

    private var listDays = ArrayList<Day>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listDays.add(Day(1, Date(), 10, 10.0.toFloat(), 10.0.toFloat()))
        listDays.add(Day(2, Date(), 10, 10.0.toFloat(), 10.0.toFloat()))
        listDays.add(Day(3, Date(), 10, 10.0.toFloat(), 10.0.toFloat()))
        listDays.add(Day(4, Date(), 10, 10.0.toFloat(), 10.0.toFloat()))
        listDays.add(Day(5, Date(), 10, 10.0.toFloat(), 10.0.toFloat()))
        listDays.add(Day(6, Date(), 10, 10.0.toFloat(), 10.0.toFloat()))
        listDays.add(Day(7, Date(), 10, 10.0.toFloat(), 10.0.toFloat()))
        listDays.add(Day(8, Date(), 10, 10.0.toFloat(), 10.0.toFloat()))
        listDays.add(Day(9, Date(), 10, 10.0.toFloat(), 10.0.toFloat()))

        var daysAdapter = DaysAdapter(activity, listDays)
        lvDays.adapter = daysAdapter
        lvDays.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            Toast.makeText(activity, "Click on " + listDays[position].id, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_stats, container, false)
    }
}