package com.tla.uchattut.presentation.chat

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.*
import com.tla.uchattut.presentation._common.dialogs.AppAlertDialog
import com.tla.uchattut.presentation.chat.model.ChatPresentationModel
import com.tla.uchattut.presentation.chat.model.MessagePresentationModel
import kotlinx.android.synthetic.main.fragment_chat.*
import javax.inject.Inject

class ChatFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModel<ChatViewModel>(viewModelFactory)
    }

    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter

    private val onActionItemClickListener =
        object : PrimaryActionModeCallback.OnActionModeMenuClickListener {
            override fun onMenuItemSelected(item: MenuItem) {
                val selectedMessages = chatRecyclerAdapter.getSelectedMessages()
                when (item.itemId) {
                    R.id.replyItem -> onReplyItemClick(selectedMessages)
                    R.id.copyItem -> onCopyItemClick(selectedMessages)
                    R.id.forwardItem -> onForwardItemClick(selectedMessages)
                    R.id.removeItem -> onDeleteItemClick(selectedMessages)
                }
            }
        }
    private val actionModeDelegate =
        ActionModeSelectedItemsDelegate<MessagePresentationModel>(onActionItemClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        DaggerContainer.chatComponent(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupActionBar()

        editText.addTextChangedListener { text ->
            if (text?.isNotBlank() == true) {
                sendImageView.setImageResource(R.drawable.ic_send_active)
            } else {
                sendImageView.setImageResource(R.drawable.ic_send_passive)
            }
        }

        sendImageView.setOnClickListener {
            if (editText.text.isNullOrBlank()) return@setOnClickListener
            sendMessage(editText.text)
        }

        chatRecyclerAdapter =
            ChatRecyclerAdapter(
                onItemClick = this::showContextMenu,
                actionModeDelegate = actionModeDelegate
            )
        chatRecyclerView.layoutManager = LinearLayoutManager(view.context).apply {
            stackFromEnd = true
        }
        chatRecyclerView.adapter = chatRecyclerAdapter

        viewModel.state.observe(viewLifecycleOwner, Observer<ChatViewModel.State> {
            updateState(it)
        })

        viewModel.getChatLiveData().observe(viewLifecycleOwner, Observer<ChatPresentationModel> {
            chatRecyclerAdapter.setMessages(it.messages)
        })

        val bundle = arguments
        val id = bundle!!.getInt("id", 0)

        viewModel.requestChat(id)
    }

    private fun setupActionBar() {
        (activity as AppCompatActivity).setSupportActionBar(chatToolbar)
        (activity as AppCompatActivity).supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    private fun sendMessage(text: Editable) {
        viewModel.sendMessage(text.toString())
        text.clear()
    }

    private fun updateState(state: ChatViewModel.State) =
        when (state) {
            ChatViewModel.State.EMPTY -> {
                contentLayout.visibility = View.GONE
                loadingLayout.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE
            }
            ChatViewModel.State.LOADING -> {
                contentLayout.visibility = View.GONE
                loadingLayout.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
            }
            ChatViewModel.State.CONTENT -> {
                contentLayout.visibility = View.VISIBLE
                loadingLayout.visibility = View.GONE
                emptyLayout.visibility = View.GONE
            }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
            else -> return false
        }
        return true
    }

    private fun showContextMenu(messageId: Int) {
    }

    private fun onReplyItemClick(selectedMessages: List<MessagePresentationModel>) {
        toast("Ответ")
        actionModeDelegate.finishActionMode()
    }

    private fun onCopyItemClick(selectedMessages: List<MessagePresentationModel>) {
        viewModel.copyMessages(context!!, selectedMessages)
        actionModeDelegate.finishActionMode()
    }

    private fun onForwardItemClick(selectedMessages: List<MessagePresentationModel>) {
        toast("Переслать")
        actionModeDelegate.finishActionMode()
    }

    private fun onDeleteItemClick(selectedMessages: List<MessagePresentationModel>) {
        AppAlertDialog.show(
            fragmentManager!!,
            "Вы действителььно хотите удлаить сообщение? "
        ) {
            viewModel.removeMessages(selectedMessages)
            actionModeDelegate.finishActionMode()
        }
    }
}