package com.tla.uchattut.presentation.schedule.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.tla.uchattut.R
import kotlinx.android.synthetic.main.dialog_notification_selector.*

class NotificationSelectorDialog private constructor() : DialogFragment() {

    private lateinit var onNotificationSelected: (notifyBefore: String) -> Unit

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

        val notificationStringArray = resources.getStringArray(R.array.notifications)
        rGroupNotification.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioBtn5min -> onNotificationSelected(notificationStringArray[0])
                R.id.radioBtn10min -> onNotificationSelected(notificationStringArray[1])
                R.id.radioBtn30min -> onNotificationSelected(notificationStringArray[2])
                R.id.radioBtn1h -> onNotificationSelected(notificationStringArray[3])
                R.id.radioBtn2h -> onNotificationSelected(notificationStringArray[4])
                }
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager, onNotificationSelected: (notifyBefore: String) -> Unit) {
            val notificationSelectorDialog = NotificationSelectorDialog()
            notificationSelectorDialog.onNotificationSelected = onNotificationSelected
            notificationSelectorDialog.show(fragmentManager, NotificationSelectorDialog::class.java.toString())
        }
    }
}