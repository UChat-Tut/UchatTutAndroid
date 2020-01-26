package com.tla.uchattut.presentation.schedule.dialogs.color_picker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.R
import kotlinx.android.synthetic.main.dialog_color_picker.*

class ColorPickerDialog private constructor(
    private val onColorPicked: (color: Int) -> Unit
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
        return inflater.inflate(R.layout.dialog_color_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorRecyclerView.adapter = ColorPickerAdapter(context!!, ::pickColorAndCloseDialog)
        colorRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun pickColorAndCloseDialog(color: Int) {
        onColorPicked(color)
        dismiss()
    }

    companion object {
        fun show(fragmentManager: FragmentManager, onColorPicked: (color: Int) -> Unit = {}) {
            val colorPickerDialog = ColorPickerDialog(onColorPicked)
            colorPickerDialog.show(fragmentManager, ColorPickerDialog::class.java.toString())
        }
    }
}