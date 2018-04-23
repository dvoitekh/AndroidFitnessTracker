package com.example.dvoitekh.fitnesstracker

import android.app.*
import android.content.Intent
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import android.util.Log
import com.example.dvoitekh.fitnesstracker.step_detection.StepDetector


class BackgroundIntentService : Service(), SensorEventListener {
    private val PERSON_STEP_SIZE = 0.8f // meters
    private val PERSON_WEIGHT = 80_000f // grams
    private val CAL_PER_GRAM_PER_METER = 0.000_000_5 // kilocalories per gram per meter

    private var numSteps: Long = 0
    private lateinit var simpleStepDetector: StepDetector
    private lateinit var sensorManager: SensorManager

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (countSensor != null) {
            sensorManager.registerListener(
                    this,
                    countSensor,
                    SensorManager.SENSOR_DELAY_UI
            )
        }

        simpleStepDetector = StepDetector { _: Long ->
            numSteps++
            val distance = numSteps * PERSON_STEP_SIZE
            val kiloCalories = CAL_PER_GRAM_PER_METER * PERSON_WEIGHT * distance
            Log.d("MyService", numSteps.toString() + distance.toString() + kiloCalories.toString())
        }
        return Service.START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            simpleStepDetector.updateAccel(event.timestamp, event.values[0],
                    event.values[1], event.values[2])
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}
