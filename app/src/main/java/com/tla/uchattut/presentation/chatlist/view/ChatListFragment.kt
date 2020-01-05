package com.tla.uchattut.presentation.chatlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.chatlist.models.ChatRepoModel
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.chatlist.view_model.ChatListViewModel
import kotlinx.android.synthetic.main.fragment_chat_list.*

class ChatListFragment : Fragment() {

    private lateinit var navController: NavController
    private val viewModel by lazy {
        viewModel { ChatListViewModel() }
    }

    private lateinit var chatListAdapter: ChatListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        chatListAdapter = ChatListRecyclerAdapter { id ->
            openChat(id)
        }
        chatListRecyclerView.layoutManager = LinearLayoutManager(context!!)
        chatListRecyclerView.adapter = chatListAdapter

        viewModel.chatList.observe(viewLifecycleOwner, Observer<List<ChatRepoModel>> {
            chatListAdapter.setChatsList(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            updateState(state)
        })

        viewModel.requestChatList()
    }

    private fun openChat(id: Int) {
        val action = ChatListFragmentDirections.actionNavigationChatToChatFragment(id = id)
        navController.navigate(action)
    }

    private fun updateState(state: ChatListViewModel.State) =
        when (state) {
            ChatListViewModel.State.LOADING -> {
                loadingLayout.visibility = View.VISIBLE
                contentLayout.visibility = View.GONE
                emptyLayout.visibility = View.GONE
            }
            ChatListViewModel.State.CONTENT -> {
                loadingLayout.visibility = View.GONE
                contentLayout.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
            }
            ChatListViewModel.State.EMPTY -> {
                loadingLayout.visibility = View.GONE
                contentLayout.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE
            }
        }
}