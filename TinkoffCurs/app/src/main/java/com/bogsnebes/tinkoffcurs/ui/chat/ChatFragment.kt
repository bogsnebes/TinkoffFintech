package com.bogsnebes.tinkoffcurs.ui.chat

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bogsnebes.tinkoffcurs.R
import com.bogsnebes.tinkoffcurs.ui.channels.viewPager.recycler.TopicItem
import com.bogsnebes.tinkoffcurs.ui.chat.recycler.DialogEmojiAdapter
import com.bogsnebes.tinkoffcurs.ui.chat.recycler.MessageAdapter
import okhttp3.internal.toHexString

class ChatFragment : Fragment() {
    private lateinit var viewModel: ChatViewModel
    private var sendButtonFlag: Boolean = false
    private val topicItem: TopicItem
        get() = requireArguments().getSerializable(CHAT) as TopicItem


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
        val recyclerMessage: RecyclerView = view.findViewById(R.id.messageRv)
        val progressBar: ProgressBar = view.findViewById(R.id.chatProgressBar)

        val messageAdapter =
            MessageAdapter(
                view.context,
                mutableListOf(),
                USER_ID,
                callbackAddReaction = { holder, messageId ->
                    showBottomDialog(view.context, holder, messageId)
                },
                callbackReaction = { reactionButton, flexBoxLayout, messageId ->
                    viewModel.clickReaction(
                        messageId,
                        reactionButton.emoji.toCharArray()[0].toInt().toString(),
                        !reactionButton.isSelected,
                        callbackAddReaction = {
                            reactionButton.isSelected = !reactionButton.isSelected
                            reactionButton.setTextColor(Color.WHITE)
                            reactionButton.countReactions++
                            if (reactionButton.countReactions <= 0) {
                                flexBoxLayout.removeView(reactionButton)
                            }
                        },
                        callbackRemoveReaction = {
                            reactionButton.isSelected = !reactionButton.isSelected
                            reactionButton.setTextColor(Color.GRAY)
                            reactionButton.countReactions--
                            if (reactionButton.countReactions <= 0) {
                                flexBoxLayout.removeView(reactionButton)
                            }
                        }
                    )
                })

        header.text = topicItem.category
        topic.text = getString(R.string.topic) + ": " + topicItem.name
        backButton.setOnClickListener {
            parentFragmentManager
                .popBackStack()
        }

        recyclerMessage.apply {
            layoutManager =
                LinearLayoutManager(view.context).apply { stackFromEnd = true }
            adapter = messageAdapter
        }

        sendMessageButton.load(R.drawable.ic_button_cross)
        sendMessageButton.setOnClickListener {
            if (sendButtonFlag) {
                viewModel.sendMessage(topicItem.category, topicItem.name, editText.text.toString())
            } else {
                Toast.makeText(view.context, getString(R.string.test), Toast.LENGTH_SHORT)
                    .show()
            }
        }
        editText.doAfterTextChanged {
            if (isEmpty(editText)) {
                sendButtonFlag = false
                context?.let {
                    sendMessageButton.setImageDrawable(
                        ContextCompat.getDrawable(
                            it,
                            R.drawable.ic_button_cross
                        )
                    )
                }

            } else {
                sendButtonFlag = true
                sendMessageButton.setImageDrawable(resources.getDrawable(R.drawable.ic_button_send_message))
            }
        }

        viewModel.getMessages(topicItem.name)

        viewModel.chatScreenState.observe(viewLifecycleOwner) {
            when (it) {
                is ChatScreenState.Result -> {
                    messageAdapter.setItems(it.items)
                    progressBar.isVisible = false
                }
                is ChatScreenState.Loading -> {
                    progressBar.isVisible = true
                }
                is ChatScreenState.SendError -> {
                    Toast.makeText(view.context, getString(R.string.error), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(view.context, getString(R.string.error), Toast.LENGTH_SHORT)
                        .show()
                    progressBar.isVisible = false
                }
            }
        }
    }

    private fun isEmpty(editText: EditText): Boolean {
        return editText.text.toString().trim { it <= ' ' }.isEmpty()
    }

    private fun showBottomDialog(
        context: Context,
        holder: MessageAdapter.ViewHolder,
        messageId: Long
    ) {
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
                    viewModel.addReaction(
                        messageId,
                        it.toCharArray()[0].toInt().toHexString(),
                        holder,
                        reactionListener = { reactionButton, flexBoxLayout ->
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
                        },
                        addReactionListener = {
                            showBottomDialog(context, holder, messageId)
                        })
                    bottomSheetDialog.dismiss()
                }
        }
    }

    companion object {
        private const val CHAT: String = "CHAT"
        const val USER_ID = 454965

        fun newInstance(topicItem: TopicItem) = ChatFragment().apply {
            arguments = bundleOf(CHAT to topicItem)
        }
    }
}