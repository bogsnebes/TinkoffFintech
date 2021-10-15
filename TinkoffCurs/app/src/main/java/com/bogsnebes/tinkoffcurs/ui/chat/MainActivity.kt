package com.bogsnebes.tinkoffcurs.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.MessageDto
import com.bogsnebes.tinkoffcurs.data.dto.ReactionDto

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var recyclerMessage: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerMessage = findViewById(R.id.messageRv)
        // Testing data
        val testListRecycler = listOf(
            MessageDto(
                123, "popka", "kupapa", null, listOf(
                    ReactionDto(123, "\uD83D\uDE00", 1), ReactionDto(123, "\uD83D\uDE00", 123451)
                )
            ),
            MessageDto(
                432,
                "popka228",
                "чупапа муняня",
                null,
                listOf(
                    ReactionDto(123, "\uD83D\uDE00", 1),
                    ReactionDto(123, "\uD83D\uDE00", 123451)
                )
            )
        )
        recyclerMessage.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        recyclerMessage.adapter = MessageAdapter(this, testListRecycler)
    }
}