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
import com.example.dvoitekh.fitnesstracker.helpers.MyDatabaseOpenHelper
import com.example.dvoitekh.fitnesstracker.models.Day
import java.util.*
import kotlinx.android.synthetic.main.fragment_stats.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

import java.util.Random

val random = Random()

fun rand(from: Int, to: Int) : Int {
    return random.nextInt(to - from) + from
}


class StatsFragment : Fragment() {

    private var listDays = ArrayList<Day>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = MyDatabaseOpenHelper.getInstance(context)

        database.use {
            delete("Day", null, null)
        }

        database.use {
            insert("Day",
                    "date" to System.currentTimeMillis(),
                    "steps" to rand(1, 1000),
                    "calories" to rand(1, 1000).toFloat(),
                    "distance" to rand(1, 1000).toFloat()
            )
        }

        listDays = database.use {
            select("Day").parseList(classParser<Day>())
        } as ArrayList<Day>

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