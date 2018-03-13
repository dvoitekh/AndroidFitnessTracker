package com.example.dvoitekh.fitnesstracker.adapters

import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.dvoitekh.fitnesstracker.R
import com.example.dvoitekh.fitnesstracker.models.Day
import java.text.SimpleDateFormat
import java.util.*


fun toSimpleString(date: Date?) = with(date ?: Date()) {
    SimpleDateFormat("yyyy-MM-dd").format(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

class DaysAdapter(val context: Context, val daysList: ArrayList<Day>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolder

        if (convertView == null) {
            view = parent?.inflate(R.layout.day)
            vh = ViewHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolder
        }

        vh.id.text = daysList[position].id.toString()
        vh.date.text = toSimpleString(daysList[position].date)
        vh.steps.text = daysList[position].steps.toString()
        vh.calories.text = daysList[position].calories.toString()
        vh.distance.text = daysList[position].distance.toString()

        return view
    }

    override fun getItem(position: Int): Any {
        return daysList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return daysList.size
    }

    private class ViewHolder(view: View?) {
        val id: TextView = view?.findViewById<TextView>(R.id.dayId) as TextView
        val date: TextView = view?.findViewById<TextView>(R.id.dayDate) as TextView
        val steps: TextView = view?.findViewById<TextView>(R.id.daySteps) as TextView
        val calories: TextView = view?.findViewById<TextView>(R.id.dayCalories) as TextView
        val distance: TextView = view?.findViewById<TextView>(R.id.dayDistance) as TextView
    }
}