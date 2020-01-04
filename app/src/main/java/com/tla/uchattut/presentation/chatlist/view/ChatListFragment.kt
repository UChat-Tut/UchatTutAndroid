package com.tla.uchattut.presentation.chatlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tla.uchattut.R
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.chatlist.view_model.ChatListViewModel

class ChatListFragment : Fragment() {

    private val viewModel = viewModel {
        ChatListViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}