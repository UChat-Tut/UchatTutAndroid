package com.tla.uchattut.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tla.uchattut.R
import com.tla.uchattut.domain._common.UniqueQueue
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation.conversation.conversation.ConversationFragment
import com.tla.uchattut.presentation.library.LibraryFragment
import com.tla.uchattut.presentation.profile.myprofile.MyProfileFragment
import com.tla.uchattut.presentation.schedule.ScheduleFragment
import com.tla.uchattut.presentation.tasks.TasksFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    private val tabsQueue = UniqueQueue<Int>()
    private val mapItemToTile = hashMapOf(
        R.id.navigation_conversation to R.string.nav_title_conversation,
        R.id.navigation_library to R.string.nav_title_library,
        R.id.navigation_schedule to R.string.nav_title_schedule,
        R.id.navigation_tasks to R.string.nav_title_tasks,
        R.id.navigation_profile to R.string.nav_title_profile
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nav_view.setOnNavigationItemSelectedListener {
            if (it.isChecked) return@setOnNavigationItemSelectedListener false
            selectBottomNavItem(it.itemId)
            return@setOnNavigationItemSelectedListener true
        }

        selectBottomNavItem(R.id.navigation_conversation)
    }

    private fun selectBottomNavItem(itemId: Int) {
        tabsQueue.offer(itemId)
        nav_view.menu.findItem(itemId).isChecked = true
        showFragmentByItemId(itemId)
    }

    private fun showFragmentByItemId(itemId: Int) {
        when (itemId) {
            R.id.navigation_conversation -> {
                replaceScreen(ConversationFragment())
            }
            R.id.navigation_library -> {
                replaceScreen(LibraryFragment())
            }
            R.id.navigation_schedule -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.mainFragmentContainer, ScheduleFragment(), ScheduleFragment.TAG)
                    .commitNow()
            }
            R.id.navigation_tasks -> {
                replaceScreen(TasksFragment())
            }
            R.id.navigation_profile -> {
                replaceScreen(MyProfileFragment())
            }
        }
    }

    private fun replaceScreen(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
            .commitNow()
    }

    fun addScreen(fragment: Fragment, tag: String? = null) {
        childFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, fragment, tag)
            .addToBackStack(null) // TODO Уюрать null
            .commit()
    }

    override fun onBackPressed(): Boolean {
        var isOnBackPressHandled = super.onBackPressed()
        if (!isOnBackPressHandled) {
            tabsQueue.poll()
            val prevItemId = tabsQueue.peek()
            if (prevItemId != null) {
                selectBottomNavItem(prevItemId)
                isOnBackPressHandled = true
            }
        }
        return isOnBackPressHandled
    }
}