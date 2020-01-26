package com.tla.uchattut.data.repositories.colors

import android.content.Context
import com.tla.uchattut.R
import com.tla.uchattut.presentation.schedule.dialogs.color_picker.ColorPickerModel

object ColorsRepository {

    private var colorDialogModels: List<ColorPickerModel>? = null

    fun getColors(context: Context): List<ColorPickerModel> {
        val resources = context.resources!!

        if (colorDialogModels == null) {
            colorDialogModels = listOf(
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_blue),
                    colorName = resources.getString(R.string.task_label_blue)
                ),
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_green),
                    colorName = resources.getString(R.string.task_label_green)
                ),
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_light_blue),
                    colorName = resources.getString(R.string.task_label_light_blue)
                ),
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_light_green),
                    colorName = resources.getString(R.string.task_label_light_green)
                ),
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_lilac),
                    colorName = resources.getString(R.string.task_label_lilac)
                ),
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_pink),
                    colorName = resources.getString(R.string.task_label_pink)
                ),
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_orange),
                    colorName = resources.getString(R.string.task_label_orange)
                ),
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_red),
                    colorName = resources.getString(R.string.task_label_red)
                ),
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_yellow),
                    colorName = resources.getString(R.string.task_label_yellow)
                ),
                ColorPickerModel(
                    color = resources.getColor(R.color.task_label_violet),
                    colorName = resources.getString(R.string.task_label_violet)
                )
            )
        }
        return colorDialogModels!!
    }

    fun getStringByColor(context: Context, color: Int): String =
        getColors(context).first { it.color == color }.colorName
}