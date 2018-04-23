package com.example.dvoitekh.fitnesstracker.fragments

import android.hardware.SensorEventListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.widget.Toast
import com.example.dvoitekh.fitnesstracker.BackgroundIntentService
import com.example.dvoitekh.fitnesstracker.R
import com.example.dvoitekh.fitnesstracker.step_detection.StepDetector
import kotlinx.android.synthetic.main.fragment_today.*


class TodayFragment : Fragment(), SensorEventListener {
    companion object {
        fun newInstance(): TodayFragment {
            val fragmentToday = TodayFragment()
            val args = Bundle()
            fragmentToday.arguments = args
            return fragmentToday
        }
        private val PERSON_STEP_SIZE = 0.8f // meters
        private val PERSON_WEIGHT = 80_000f // grams
        private val CAL_PER_GRAM_PER_METER = 0.000_000_5 // kilocalories per gram per meter
    }

    private var activityRunning: Boolean = false

    private var numSteps: Long = 0

    private lateinit var simpleStepDetector: StepDetector

    private lateinit var sensorManager: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = activity.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val serviceIntent = Intent(context, BackgroundIntentService::class.java)
        context.startService(serviceIntent)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stepsTxt.text = 0.toString()
        distanceTxt.text = "%.2f".format(.0)
        caloriesTxt.text = "%.2f".format(.0)

        simpleStepDetector = StepDetector { _: Long ->
            numSteps++
            val distance = numSteps * PERSON_STEP_SIZE
            val kiloCalories = CAL_PER_GRAM_PER_METER * PERSON_WEIGHT * distance

            stepsTxt.text = numSteps.toString()
            distanceTxt.text = "%.2f".format(distance)
            caloriesTxt.text = "%.2f".format(kiloCalories)
        }
    }

    override fun onResume() {
        super.onResume()
        val countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (countSensor != null) {
            sensorManager.registerListener(
                    this,
                    countSensor,
                    SensorManager.SENSOR_DELAY_UI
            )
        } else {
            Toast.makeText(activity, "Sensor not available", Toast.LENGTH_SHORT).show()
        }
        activityRunning = true
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        activityRunning = false
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (activityRunning && event != null) {
            simpleStepDetector.updateAccel(event.timestamp, event.values[0],
                    event.values[1], event.values[2])
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_today, container, false)
    }
}
