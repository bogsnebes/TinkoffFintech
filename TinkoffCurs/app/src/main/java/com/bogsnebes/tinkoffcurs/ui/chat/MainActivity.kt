package com.bogsnebes.tinkoffcurs.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var recyclerMessage: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerMessage = findViewById(R.id.messageRv)
        recyclerMessage.adapter = MessageAdapter(this, TODO())
    }
}