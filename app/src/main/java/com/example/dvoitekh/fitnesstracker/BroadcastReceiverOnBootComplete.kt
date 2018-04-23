package com.example.dvoitekh.fitnesstracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BroadcastReceiverOnBootComplete : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, BackgroundIntentService::class.java)
        context.startService(serviceIntent)
    }
}
