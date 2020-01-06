package com.tla.uchattut.presentation._common

import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.MenuRes

class PrimaryActionModeCallback(
    private val onActionItemClickListener: OnActionModeClickListener? = null,
    private val finishCallback: () -> Unit
) : ActionMode.Callback {

    private var mode: ActionMode? = null
    @MenuRes
    private var menuResId: Int = 0
    private var title: String? = null
    private var subtitle: String? = null

    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        this.mode = mode
        mode.menuInflater.inflate(menuResId, menu)
        mode.title = title
        mode.subtitle = subtitle
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        return false
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        this.mode = null
        finishCallback()
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        onActionItemClickListener?.onMenuItemSelected(item)
        mode.finish()
        return true
    }

    fun startActionMode(
        view: View,
        @MenuRes menuResId: Int,
        title: String? = null,
        subtitle: String? = null
    ) {
        this.menuResId = menuResId
        this.title = title
        this.subtitle = subtitle
        view.startActionMode(this)
    }

    fun finishActionMode() {
        mode?.finish()
    }

    fun isActive(): Boolean =
        mode != null

    fun isNotActive(): Boolean =
        !isActive()

    interface OnActionModeClickListener {
        fun onMenuItemSelected(item: MenuItem)
        fun selectItem(view: View)
        fun unSelectItem(view: View)
    }
}