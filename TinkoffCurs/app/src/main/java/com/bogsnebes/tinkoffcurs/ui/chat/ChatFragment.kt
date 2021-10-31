package com.bogsnebes.tinkoffcurs.ui.chat

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.data.dto.ChatDto
import com.bogsnebes.tinkoffcurs.data.dto.MessageDto
import com.bogsnebes.tinkoffcurs.ui.chat.recycler.DialogEmojiAdapter
import com.bogsnebes.tinkoffcurs.ui.chat.recycler.MessageAdapter

class ChatFragment : Fragment() {
    private lateinit var viewModel: ChatViewModel
    private var sendButtonFlag: Boolean = false
    private val chatDto: ChatDto
        get() = requireArguments().getSerializable(CHAT) as ChatDto


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        val sendMessageButton: ImageButton = view.findViewById(R.id.sendMessageIb)
        val editText: EditText = view.findViewById(R.id.editText)
        val header: TextView = view.findViewById(R.id.headerChatTv)
        val topic: TextView = view.findViewById(R.id.topicChatTv)
        val backButton: ImageButton = view.findViewById(R.id.backChatIb)
        val messageAdapter = MessageAdapter(view.context, chatDto.messages) {
            showBottomDialog(view.context, it)
        }
        header.text = chatDto.category
        topic.text = getString(R.string.topic) + ": " + chatDto.name
        backButton.setOnClickListener {
            parentFragmentManager
                .popBackStack()
        }
        val recyclerMessage: RecyclerView = view.findViewById(R.id.messageRv)
        recyclerMessage.layoutManager =
            LinearLayoutManager(view.context).apply { stackFromEnd = true }
        recyclerMessage.adapter = messageAdapter
        var id = 1
        sendMessageButton.load(R.drawable.ic_button_cross)
        sendMessageButton.setOnClickListener {
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
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isEmpty(editText)) {
                    sendButtonFlag = false
                    sendMessageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_button_cross))
                } else {
                    sendButtonFlag = true
                    sendMessageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_button_send_message))
                }
            }
        })
    }

    private fun isEmpty(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.isEmpty()
    }

    private fun showBottomDialog(context: Context, holder: MessageAdapter.ViewHolder) {
        val bottomSheetDialog = Dialog(context)
        bottomSheetDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        bottomSheetDialog.setContentView(R.layout.dialog_emoji)
        bottomSheetDialog.show()
        bottomSheetDialog.window?.let { window ->
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            window.attributes.windowAnimations = R.style.DialogAnimation
            window.setGravity(Gravity.BOTTOM)
            val listEmoji: List<String> = context.getString(R.string.emoji).split(" ")
            val recyclerView = window.findViewById<RecyclerView>(R.id.emojiRv)
            recyclerView.layoutManager =
                GridLayoutManager(context, 7)
            recyclerView.adapter =
                DialogEmojiAdapter(context, listEmoji) {
                    holder.messageView.addReaction(it, 1)
                    holder.messageView.setOnReactionClickListener { reactionButton, flexBoxLayout ->
                        reactionButton.isSelected = !reactionButton.isSelected
                        if (!reactionButton.isSelected) {
                            reactionButton.setTextColor(Color.WHITE)
                            reactionButton.countReactions++
                        } else {
                            reactionButton.setTextColor(Color.GRAY)
                            reactionButton.countReactions--
                        }
                        if (reactionButton.countReactions <= 0) {
                            flexBoxLayout.removeView(reactionButton)
                        }
                    }
                    holder.messageView.setOnAddReactionClickListener {
                        showBottomDialog(context, holder)
                    }
                    bottomSheetDialog.dismiss()
                }
        }
    }

    companion object {
        private const val CHAT: String = "CHAT"

        fun newInstance(chatDto: ChatDto) = ChatFragment().apply {
            arguments = bundleOf(CHAT to chatDto)
        }
    }
}