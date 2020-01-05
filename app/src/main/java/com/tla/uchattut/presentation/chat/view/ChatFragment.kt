package com.tla.uchattut.presentation.chat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.R
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.chat.view_model.ChatViewModel
import com.tla.uchattut.presentation.chat.view_model.model.MessagePresentationModel
import kotlinx.android.synthetic.main.fragment_chat.*

class ChatFragment : Fragment() {

    private lateinit var chatRecyclerAdapter: ChatRecyclerAdapter
    private val viewModel: ChatViewModel by lazy {
        viewModel { ChatViewModel() }
    }
    private val args: ChatFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatRecyclerAdapter = ChatRecyclerAdapter()
        chatRecyclerView.layoutManager = LinearLayoutManager(view.context)
        chatRecyclerView.adapter = chatRecyclerAdapter

        viewModel.state.observe(viewLifecycleOwner, Observer<ChatViewModel.State> {
            updateState(it)
        })

        viewModel.messageList.observe(viewLifecycleOwner, Observer<List<MessagePresentationModel>> {
            chatRecyclerAdapter.setMessages(it)
        })

        viewModel.requestMessages(args.id)
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
}