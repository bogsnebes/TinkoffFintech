package com.bogsnebes.tinkoff

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var textView: TextView

    private val startForResult = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val intent = it.data
            findViewById<TextView>(R.id.textView).text =
                intent?.getStringExtra(MyService.EXTRA_TEXT_FROM_SERVICE)
        }
    }

    private val singlePermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            when {
                granted -> {
                    startForResult.launch(SecondActivity.createInstance(this))
                }
                else -> {
                    Toast.makeText(this, "Предоставьте доступ к контактам", Toast.LENGTH_SHORT)
                        .show()
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.READ_CONTACTS),
                        0
                    )
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        textView = findViewById(R.id.textView)
        if (savedInstanceState != null) {
            savedInstanceState.getString(SIS_TEXTVIEW)?.let {
                textView.text = it
            }
        } else {
            textView.text = getString(R.string.first_activity)
        }
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            singlePermission.launch(android.Manifest.permission.READ_CONTACTS)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(
            SIS_TEXTVIEW,
            textView.text.toString()
        )
    }

    companion object {
        private const val SIS_TEXTVIEW = "SIS_TEXTVIEW"
    }
}