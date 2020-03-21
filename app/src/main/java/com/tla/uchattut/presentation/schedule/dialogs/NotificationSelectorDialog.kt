package com.tla.uchattut.presentation.schedule.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.tla.uchattut.R
import kotlinx.android.synthetic.main.dialog_notification_selector.*

class NotificationSelectorDialog private constructor(
    private val onNotificationSelected: (notifyBefore: String) -> Unit
) : DialogFragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rGroupNotification.setOnCheckedChangeListener{_, checkedId ->
           when(checkedId) {
               R.id.radioBtn5min -> onNotificationSelected("За 5 минут")
               R.id.radioBtn10min -> onNotificationSelected("За 10 минут")
               R.id.radioBtn30min -> onNotificationSelected("За 30 минут")
               R.id.radioBtn1h -> onNotificationSelected("За час")
               R.id.radioBtn2h -> onNotificationSelected("За 2 часа")

           }
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager, onNotificationSelected: (notifyBefore: String) -> Unit) {
            val notificationSelectorDialog = NotificationSelectorDialog(onNotificationSelected)
            notificationSelectorDialog.show(fragmentManager, NotificationSelectorDialog::class.java.toString())
        }
    }
}