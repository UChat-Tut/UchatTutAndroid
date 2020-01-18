package com.tla.uchattut.presentation.schedule.view.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.tla.uchattut.R

class RepeatingSelectorDialog private constructor(): DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_repeating_selector, container, false)
    }

    companion object {
        fun show(fragmentManager: FragmentManager) {
            val repeatingSelectorDialog = RepeatingSelectorDialog()
            repeatingSelectorDialog.show(fragmentManager, RepeatingSelectorDialog::class.java.toString())
        }
    }
}