package com.bogsnebes.tinkoff

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var button: Button
    private lateinit var textView: TextView

    private val startForResult = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val intent = it.data
            findViewById<TextView>(R.id.textView).text =
                intent?.getStringExtra(SecondActivity.TEXT_FROM_SERVICE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = findViewById(R.id.textView)
        textView
        if (savedInstanceState != null) {
            savedInstanceState.getString(KEY)?.let {
                textView.text = it
            }
        } else {
            textView.text = getString(R.string.hello_world)
        }
        button = findViewById(R.id.button)
        button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            )
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_CONTACTS),
                    0
                )
            else
                startForResult.launch(SecondActivity.createInstance(this))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            KEY,
            textView.text.toString()
        )
    }

    companion object {
        private const val KEY = "KEY"
    }
}