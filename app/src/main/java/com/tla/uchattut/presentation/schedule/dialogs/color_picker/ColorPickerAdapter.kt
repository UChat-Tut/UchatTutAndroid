package com.tla.uchattut.presentation.schedule.dialogs.color_picker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.colors.ColorsRepository
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_color_picker.*

class ColorPickerAdapter(
    context: Context,
    private val onColorPicked: (color: Int) -> Unit = {}
) : RecyclerView.Adapter<ColorPickerAdapter.ColorPickerViewHolder>() {

    private val colorModels = ColorsRepository.getColors(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorPickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_color_picker, parent, false)
        return ColorPickerViewHolder(view, onColorPicked)
    }

    override fun getItemCount(): Int = colorModels.size

    override fun onBindViewHolder(holder: ColorPickerViewHolder, position: Int) {
        holder.bind(colorModels[position])
    }

    class ColorPickerViewHolder(
        override val containerView: View,
        private val onColorPicked: (color: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(colorModel: ColorDialogModel) {
            colorCardView.setCardBackgroundColor(colorModel.color)
            colorTextView.text = colorModel.colorName
            containerView.setOnClickListener {
                onColorPicked(colorModel.color)
            }
        }
    }
}