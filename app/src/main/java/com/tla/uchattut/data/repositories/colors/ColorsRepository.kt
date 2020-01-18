package com.tla.uchattut.presentation.schedule.view.dialogs.color_picker

import android.content.Context
import android.graphics.Color

object ColorsRepository {
    fun getColors(context: Context) : List<ColorDialogModel> {
        val resources = context.resources!!

        return listOf(
            ColorDialogModel(Color.RED, "Красный"),
            ColorDialogModel(Color.BLUE, "Синий"),
            ColorDialogModel(Color.GRAY, "Серый"),
            ColorDialogModel(Color.GREEN, "Зеленый"),
            ColorDialogModel(Color.CYAN, "Циан"),
            ColorDialogModel(Color.YELLOW, "Желтый")
        )
    }
}