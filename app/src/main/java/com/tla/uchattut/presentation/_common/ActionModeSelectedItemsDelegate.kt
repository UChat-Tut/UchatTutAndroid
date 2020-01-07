package com.tla.uchattut.presentation._common

import android.view.View
import androidx.annotation.MenuRes

class ActionModeSelectedItemsDelegate<E>(
    onMenuItemListener: PrimaryActionModeCallback.OnActionModeMenuClickListener
) {

    val selectedItems = mutableListOf<E>()
    private val selectedViews = mutableListOf<View>()
    private val actionModeCallback = PrimaryActionModeCallback(
        onActionItemClickListener = onMenuItemListener,
        finishCallback = {
            unSelectAll()
            onCloseActionMode()
        }
    )

    private val clickListenersMap = HashMap<View, OnActionModeClickListener>()

    fun startActionMode(
        view: View,
        @MenuRes menuResId: Int,
        title: String? = null,
        subtitle: String? = null
    ) {
        if (actionModeCallback.isActive()) return

        actionModeCallback.startActionMode(view, menuResId, title, subtitle)
        onOpenActionMode()
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
        clickListenersMap[view]?.selectItem()
        selectedViews.add(view)
        selectedItems.add(item)
    }

    fun isActive(): Boolean =
        actionModeCallback.isActive()

    private fun unSelectItem(view: View, item: E) {
        clickListenersMap[view]?.unSelectItem()
        selectedViews.remove(view)
        selectedItems.remove(item)
    }

    private fun unSelectAll() {
        selectedItems.clear()
        selectedViews.forEach {
            clickListenersMap[it]?.unSelectItem()
        }
        selectedViews.clear()
    }

    fun addActionModeClickListener(view: View, actionModeClickListener: OnActionModeClickListener) {
        clickListenersMap[view] = actionModeClickListener
    }

    private fun onOpenActionMode() {
        clickListenersMap.entries.forEach { it.value.onOpenActionMode() }
    }

    private fun onCloseActionMode() {
        clickListenersMap.entries.forEach { it.value.onCloseActionMode() }
    }

    interface OnActionModeClickListener {
        fun selectItem()
        fun unSelectItem()

        fun onOpenActionMode() {}
        fun onCloseActionMode() {}
    }
}