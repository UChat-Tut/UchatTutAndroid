package com.tla.uchattut.presentation._common.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.tla.uchattut.R
import kotlinx.android.synthetic.main.dialog_alert.*

class AppAlertDialog private constructor(
    private val message: String,
    private val onApplyClick: () -> Unit = {}
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
        return inflater.inflate(R.layout.dialog_alert, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertMessageTextView.text = message

        applyButton.setOnClickListener {
            onApplyClick()
            dismiss()
        }
        closeButton.setOnClickListener { dismiss() }
    }

    companion object {
        fun show(
            fragmentManager: FragmentManager,
            message: String,
            onApplyClick: () -> Unit = {}
        ): DialogFragment {
            val dialog = AppAlertDialog(message, onApplyClick)
            dialog.show(fragmentManager, this::class.toString())
            return dialog
        }
    }
}