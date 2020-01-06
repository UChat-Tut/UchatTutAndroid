package com.tla.uchattut.presentation.chatlist.view

import android.view.View
import androidx.annotation.MenuRes

class ActionModeSelectItemsDelegate<E>(
    onActionItemClickListener: PrimaryActionModeCallback.OnActionItemClickListener
) {

    private val selectedItems = mutableListOf<E>()
    private val selectedViews = mutableListOf<View>()
    private val actionModeCallback = PrimaryActionModeCallback(
        onActionItemClickListener = onActionItemClickListener,
        finishCallback = this::unSelectAll
    )

    fun startActionMode(
        view: View,
        @MenuRes menuResId: Int,
        title: String? = null,
        subtitle: String? = null
    ) {
        if (actionModeCallback.isActive()) return

        actionModeCallback.startActionMode(view, menuResId, title, subtitle)
    }

    fun finishActionMode() {
        actionModeCallback.finishActionMode()
    }

    fun clickItem(view: View, item: E) {
        if (actionModeCallback.isNotActive()) return

        if (!selectedItems.contains(item)) {
            selectItem(view, item)
        } else {
            unSelectItem(view, item)
        }

        finishActionModeIfNoSelectedItems()
    }

    private fun finishActionModeIfNoSelectedItems() {
        if (selectedItems.size == 0) finishActionMode()
    }

    private fun selectItem(view: View, item: E) {
        view.isActivated = true
        selectedViews.add(view)
        selectedItems.add(item)
    }

    fun isActive(): Boolean =
        actionModeCallback.isActive()

    private fun unSelectItem(view: View, item: E) {
        view.isActivated = false
        selectedViews.remove(view)
        selectedItems.remove(item)
    }

    private fun unSelectAll() {
        selectedItems.clear()
        selectedViews.forEach {
            it.isActivated = false
        }
        selectedViews.clear()
    }
}