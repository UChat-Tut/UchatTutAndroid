package com.tla.uchattut.data.repositories.colors

import android.content.Context
import com.tla.uchattut.R
import com.tla.uchattut.presentation.schedule.view.dialogs.color_picker.ColorDialogModel

object ColorsRepository {
    fun getColors(context: Context): List<ColorDialogModel> {
        val resources = context.resources!!

        return listOf(
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_blue),
                colorName = resources.getString(R.string.task_label_blue)
            ),
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_green),
                colorName = resources.getString(R.string.task_label_green)
            ),
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_light_blue),
                colorName = resources.getString(R.string.task_label_light_blue)
            ),
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_light_green),
                colorName = resources.getString(R.string.task_label_light_green)
            ),
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_lilac),
                colorName = resources.getString(R.string.task_label_lilac)
            ),
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_pink),
                colorName = resources.getString(R.string.task_label_pink)
            ),
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_orange),
                colorName = resources.getString(R.string.task_label_orange)
            ),
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_red),
                colorName = resources.getString(R.string.task_label_red)
            ),
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_yellow),
                colorName = resources.getString(R.string.task_label_yellow)
            ),
            ColorDialogModel(
                color = resources.getColor(R.color.task_label_violet),
                colorName = resources.getString(R.string.task_label_violet)
            )
        )
    }
}