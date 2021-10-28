package com.bogsnebes.tinkoffcurs.ui.chat.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bogsnebes.tinkoffcurs.R

class DialogEmojiAdapter(
    private val context: Context,
    private val emojiList: List<String>,
    private val callbackEmoji: (emoji: String) -> Unit
) : RecyclerView.Adapter<DialogEmojiAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(context)
                .inflate(R.layout.item_dialog_emoji_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return emojiList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.emoji.text = emojiList[position]
        holder.emoji.setOnClickListener {
            callbackEmoji.invoke(emojiList[position])
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val emoji: TextView = view.findViewById(R.id.emojiTv)
    }
}