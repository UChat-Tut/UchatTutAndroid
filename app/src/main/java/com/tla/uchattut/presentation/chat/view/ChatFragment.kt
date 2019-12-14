package com.tla.uchattut.presentation.chat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.tla.uchattut.R
import com.tla.uchattut.presentation.chat.view_model.ChatViewModel
import com.tla.uchattut.presentation.resources.AndroidResourceManager
import com.tla.uchattut.presentation.viewModel

class ChatFragment : Fragment() {

    private lateinit var chatViewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = activity!!.applicationContext
        chatViewModel = viewModel { ChatViewModel(AndroidResourceManager(context))}
        val root = inflater.inflate(R.layout.fragment_chat, container, false)


        return root
    }
}