package com.example.dvoitekh.fitnesstracker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.dvoitekh.fitnesstracker.step_detection.StepDetector
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private var activityRunning: Boolean = false

    private var numSteps: Long = 0

    private lateinit var simpleStepDetector: StepDetector

    private val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        simpleStepDetector = StepDetector { timeNs: Long ->
            numSteps++
            stepsTxt.text = numSteps.toString()
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
            Toast.makeText(this, "Sensor not available", Toast.LENGTH_SHORT).show()
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
}
