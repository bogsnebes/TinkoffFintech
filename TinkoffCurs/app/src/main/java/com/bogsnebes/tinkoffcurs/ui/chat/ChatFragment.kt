package com.bogsnebes.tinkoffcurs.ui.chat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.ChatDto
import com.bogsnebes.tinkoffcurs.data.dto.MessageDto

class ChatFragment : Fragment() {
    private lateinit var recyclerMessage: RecyclerView
    private lateinit var viewModel: ChatViewModel
    private var sendButtonFlag: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        val button = view.findViewById<ImageButton>(R.id.imageButton)
        val editText = view.findViewById<EditText>(R.id.editText)
        (arguments?.getSerializable(CHAT) as? ChatDto)?.let { chatDto ->
            val messageAdapter = MessageAdapter(view.context, chatDto.messages)
            recyclerMessage = view.findViewById(R.id.messageRv)
            recyclerMessage.layoutManager =
                LinearLayoutManager(view.context).apply { stackFromEnd = true }
            recyclerMessage.adapter = messageAdapter
            var id = 1
            button.load(R.drawable.ic_button_cross)
            button.setOnClickListener {
                if (sendButtonFlag) {
                    id++
                    chatDto.messages.add(
                        MessageDto(
                            id, 123, getString(R.string.writer_1), editText.text.toString(),
                            null, listOf(), "03.01.2020"
                        )
                    )
                    recyclerMessage.adapter = messageAdapter
                } else {
                    Toast.makeText(view.context, getString(R.string.test), Toast.LENGTH_SHORT)
                        .show()
                }
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

    companion object {
        private const val CHAT: String = "CHAT"

        fun newInstance(chatDto: ChatDto) = ChatFragment().apply {
            arguments = bundleOf(CHAT to chatDto)
        }
    }
}