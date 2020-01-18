package com.tla.uchattut.presentation.schedule.view.dialogs.color_picker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_color_picker.*

class ColorPickerAdapter : RecyclerView.Adapter<ColorPickerAdapter.ColorPickerViewHolder>() {

    private val colorModels = ColorsRepository.getColors()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_color_picker, parent, false)
        return ColorPickerViewHolder(view)
    }

    override fun getItemCount(): Int = colorModels.size

    override fun onBindViewHolder(holder: ColorPickerViewHolder, position: Int) {
        holder.bind(colorModels[position])
    }

    class ColorPickerViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(colorModel: ColorDialogModel) {
            colorCardView.setBackgroundColor(colorModel.color)
            colorTextView.text = colorModel.colorName
        }
    }
}