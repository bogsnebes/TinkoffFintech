package com.bogsnebes.tinkoffcurs.ui.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.MessageDto
import com.bogsnebes.tinkoffcurs.data.dto.ReactionDto


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var recyclerMessage: RecyclerView

    // Testing data
    private val testListRecycler = mutableListOf(
        MessageDto(
            0, 123, "Писатель 1", "kappa", null, listOf(
                ReactionDto(123, "\uD83D\uDE00", 1), ReactionDto(123, "\uD83D\uDE00", 123451)
            ), "03.01.2020"
        ),
        MessageDto(
            1, 432,
            "Писатель 2",
            "чупапа муняня",
            null,
            listOf(), "03.01.2020"
        )
    )
    private val messageAdapter = MessageAdapter(this, testListRecycler)
    private var sendButtonFlag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerMessage = findViewById(R.id.messageRv)
        recyclerMessage.layoutManager = LinearLayoutManager(this).apply { stackFromEnd = true }
        recyclerMessage.adapter = messageAdapter
        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<ImageButton>(R.id.imageButton)
        button.load(R.drawable.ic_button_cross)
        var id = 1
        button.setOnClickListener {
            if (sendButtonFlag) {
                id++
                testListRecycler.add(
                    MessageDto(
                        id, 123, "Писатель 1", editText.text.toString(),
                        null, listOf(), "03.01.2020"
                    )
                )
                recyclerMessage.adapter = messageAdapter
            } else {
                Toast.makeText(this, "Прикрепите фото", Toast.LENGTH_SHORT).show()
            }
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isEmpty(editText)) {
                    sendButtonFlag = false
                    button.setImageDrawable(resources.getDrawable(R.drawable.ic_button_cross))
                } else {
                    sendButtonFlag = true
                    button.setImageDrawable(resources.getDrawable(R.drawable.ic_button_send_message))
                }
            }
        })


    }

    private fun isEmpty(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.isEmpty()
    }
}