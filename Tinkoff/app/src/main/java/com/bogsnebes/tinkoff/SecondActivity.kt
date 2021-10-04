package com.bogsnebes.tinkoff

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class SecondActivity : AppCompatActivity(R.layout.activity_second) {
    private val broadcast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let {
                setResult(
                    RESULT_OK,
                    Intent().putExtra(
                        MyService.EXTRA_TEXT_FROM_SERVICE,
                        intent.getStringExtra(MyService.EXTRA_TEXT_FROM_SERVICE)
                    )
                )
                onBackPressed()
            }
        }
    }
    private lateinit var localBroadcastManager: LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(MyService.createInstance(this))
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.registerReceiver(broadcast, intentFilt)
    }

    override fun onDestroy() {
        super.onDestroy()
        localBroadcastManager.unregisterReceiver(broadcast)
    }

    companion object {
        private val intentFilt = IntentFilter(MyService.EXTRA_BROADCAST_ACTION)

        fun createInstance(context: Context): Intent {
            return Intent(context, SecondActivity::class.java)
        }
    }
}