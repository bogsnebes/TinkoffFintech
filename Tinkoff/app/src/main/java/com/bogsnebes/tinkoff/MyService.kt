package com.bogsnebes.tinkoff

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MyService : Service() {
    init {
        Log.d(TAG, "Service is running...")
        run()
    }

    private fun run(): Int {
        Thread {
            val intent = Intent(SecondActivity.BROADCAST_ACTION)
            try {
                intent.putExtra(SecondActivity.TEXT_FROM_SERVICE, "Hello from Service!")
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            } catch (error: InterruptedException) {
                error.printStackTrace()
            }
            stopSelf()
        }.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    companion object {
        const val TAG = "MyService"
    }
}