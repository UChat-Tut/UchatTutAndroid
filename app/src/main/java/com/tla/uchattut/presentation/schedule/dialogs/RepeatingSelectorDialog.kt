package com.tla.uchattut.presentation.schedule.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.tla.uchattut.R
import kotlinx.android.synthetic.main.dialog_repeating_selector.*

class RepeatingSelectorDialog private constructor(
    private val onRepeatSelected: (repeat: String) -> Unit
) : DialogFragment(){

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
        rGroupRepeating.setOnCheckedChangeListener{ _, checkedId ->
            when(checkedId){
                R.id.radioBtnNotRepeating -> onRepeatSelected("Не повторяется")
                R.id.radioBtnEveryDay -> onRepeatSelected("Каждый день")
                R.id.radioBtnEveryWeek -> onRepeatSelected("Каждую неделю")
                R.id.radioBtnEveryMonth -> onRepeatSelected("Каждый месяц")
                R.id.radioBtnEveryYear -> onRepeatSelected("Каждый год")
            }
        }
    }

    companion object {
        fun show(fragmentManager: FragmentManager, onRepeatSelected: (repeat: String) -> Unit) {
            val repeatingSelectorDialog = RepeatingSelectorDialog(onRepeatSelected)
            repeatingSelectorDialog.show(fragmentManager, RepeatingSelectorDialog::class.java.toString())
        }
    }
}