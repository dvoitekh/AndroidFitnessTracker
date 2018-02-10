package com.example.dvoitekh.fitnesstracker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val foodList = arrayListOf("Chinese", "Burger", "Pizza")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        decideBtn.setOnClickListener {
            val random = Random()
            val randomIndex = random.nextInt(foodList.count())
            selectedFoodText.text = foodList[randomIndex]
        }

        addFoodBtn.setOnClickListener {
            val newFood = addFoodInput.text.toString()
            foodList.add(newFood)
        }
    }
}
