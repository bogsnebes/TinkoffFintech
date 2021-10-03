package com.bogsnebes.tinkoff

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var button: Button

    private val startForResult = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val intent = it.data
            findViewById<TextView>(R.id.textView).text =
                intent?.getStringExtra(SecondActivity.TEXT_FROM_SERVICE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button = findViewById(R.id.button)
        button.setOnClickListener {
            startForResult.launch(SecondActivity.createInstance(this))
        }
    }
}