package com.bogsnebes.tinkoff

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SecondActivity : AppCompatActivity(R.layout.activity_second) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    companion object {
        fun createInstance(context: Context): Intent {
            return Intent(context, SecondActivity::class.java)
        }
    }
}