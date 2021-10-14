package com.bogsnebes.tinkoffcurs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bogsnebes.tinkoffcurs.ui.custom.reaction.ReactionButton
import com.bogsnebes.tinkoffcurs.ui.custom.message.MessageView

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val messageView = findViewById<MessageView>(R.id.messageView)
        messageView.setOnReactionClickListener {
            it as ReactionButton
            it.countReactions++
        }
        messageView.setOnAddReactionClickListener {
            val emoji = ReactionButton(this).apply {
                this.emoji = "\uD83D\uDE0E"
                this.countReactions = 1
                this.setBackgroundResource(R.drawable.bg_emoji_reaction_button)
            }
            return@setOnAddReactionClickListener emoji
        }
    }
}