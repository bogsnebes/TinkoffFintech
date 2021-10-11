package com.bogsnebes.tinkoffcurs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.tinkoffcurs.ui.custom.emojireaction.ReactionButton
import com.bogsnebes.tinkoffcurs.ui.custom.emojireaction.ReactionFlexBoxLayout
import com.bogsnebes.tinkoffcurs.ui.custom.message.MessageView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val messageView = findViewById<MessageView>(R.id.mV)
        messageView.setOnAddReactionClickListener {
            val emoji = ReactionButton(this).apply {
                this.emoji = "\uD83D\uDE0E"
                this.countReactions = "1"
            }
            findViewById<ReactionFlexBoxLayout>(R.id.rfbl).addView(emoji)
        }
        messageView.setOnReactionClickListener {}
    }
}