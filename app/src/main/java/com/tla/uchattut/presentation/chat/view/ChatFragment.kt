package com.tla.uchattut.presentation.chat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tla.uchattut.R
import com.tla.uchattut.presentation.chat.view_model.ChatViewModel
import com.tla.uchattut.presentation._common.resources.AndroidResourceManager
import com.tla.uchattut.presentation._common.viewModel

class ChatFragment : Fragment() {

    private val chatViewModel: ChatViewModel by lazy {
        viewModel { ChatViewModel(AndroidResourceManager(context!!)) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }
}