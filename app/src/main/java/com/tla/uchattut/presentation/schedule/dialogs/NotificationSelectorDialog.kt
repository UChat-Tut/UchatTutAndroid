package com.tla.uchattut.presentation.schedule.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.tla.uchattut.R

class NotificationSelectorDialog private constructor() : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_notification_selector, container, false)
    }

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val notificationSelectorDialog = NotificationSelectorDialog()
            notificationSelectorDialog.show(fragmentManager, NotificationSelectorDialog::class.java.toString())
        }
    }
}