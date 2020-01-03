package com.tla.uchattut.presentation.notifications.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tla.uchattut.R
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.notifications.view_model.NotificationsViewModel
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private val notificationsViewModel: NotificationsViewModel by lazy {
        viewModel { NotificationsViewModel() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationsViewModel.text.observe(this, Observer {
            text_notifications.text = it
        })
    }
}