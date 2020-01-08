package com.tla.uchattut.presentation.chat.view

import android.os.Bundle
import android.text.Editable
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.ActionModeSelectedItemsDelegate
import com.tla.uchattut.presentation._common.PrimaryActionModeCallback
import com.tla.uchattut.presentation._common.dialogs.AppAlertDialog
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.chat.view_model.ChatViewModel
import com.tla.uchattut.presentation.chat.view_model.model.ChatPresentationModel
import com.tla.uchattut.presentation.chat.view_model.model.MessagePresentationModel
import kotlinx.android.synthetic.main.fragment_chat.*
import javax.inject.Inject

class ChatFragment : Fragment() {

    @Inject
    lateinit var viewModel: ChatViewModel

    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter
    private lateinit var navController: NavController

    private val args: ChatFragmentArgs by navArgs()
    private val onActionItemClickListener =
        object : PrimaryActionModeCallback.OnActionModeMenuClickListener {
            override fun onMenuItemSelected(item: MenuItem) {
                val selectedMessages = chatRecyclerAdapter.getSelectedMessages()
                when (item.itemId) {
                    R.id.replyItem -> onReplyItemClick(selectedMessages)
                    R.id.copyItem -> onCopyItemClick(selectedMessages)
                    R.id.forwardItem -> onForwardItemClick(selectedMessages)
                    R.id.deleteItem -> onDeleteItemClick(selectedMessages)
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

        navController = findNavController()

        chatRecyclerAdapter = ChatRecyclerAdapter(
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

        viewModel.requestChat(args.id)
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