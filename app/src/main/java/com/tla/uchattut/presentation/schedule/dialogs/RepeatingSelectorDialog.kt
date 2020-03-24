package com.tla.uchattut.presentation.schedule.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.tla.uchattut.R
import kotlinx.android.synthetic.main.dialog_repeating_selector.*

class RepeatingSelectorDialog private constructor() : DialogFragment() {

    private lateinit var onRepeatSelected: (repeat: String) -> Unit

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repeatStringArray = resources.getStringArray(R.array.repeating)
        rGroupRepeating.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioBtnNotRepeating -> onRepeatSelected(repeatStringArray[0])
                R.id.radioBtnEveryDay -> onRepeatSelected(repeatStringArray[1])
                R.id.radioBtnEveryWeek -> onRepeatSelected(repeatStringArray[2])
                R.id.radioBtnEveryMonth -> onRepeatSelected(repeatStringArray[3])
                R.id.radioBtnEveryYear -> onRepeatSelected(repeatStringArray[4])
            }
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager, onRepeatSelected: (repeat: String) -> Unit) {
            val repeatingSelectorDialog = RepeatingSelectorDialog()
            repeatingSelectorDialog.onRepeatSelected = onRepeatSelected
            repeatingSelectorDialog.show(fragmentManager, RepeatingSelectorDialog::class.java.toString())
        }
    }
}