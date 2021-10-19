package com.bogsnebes.tinkoffcurs.ui.chat

import androidx.recyclerview.widget.DiffUtil
import com.bogsnebes.tinkoffcurs.data.dto.MessageDto

// Я не стал удалять данный класс, так как не понимаю в чем ошибка.
// DiffUtil работает медленно и намного хуже, чем обычное обновление адаптера.

class MessageDiffUtil(
    private val oldList: List<MessageDto>,
    private val newList: List<MessageDto>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].userId != newList[newItemPosition].userId -> false
            oldList[oldItemPosition].sender != newList[newItemPosition].sender -> false
            oldList[oldItemPosition].message != newList[newItemPosition].message -> false
            oldList[oldItemPosition].reactions != newList[newItemPosition].reactions -> false
            else -> true
        }
    }
}