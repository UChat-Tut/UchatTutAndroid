package com.tla.uchattut.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.tla.uchattut.R
import com.tla.uchattut.domain._common.UniqueQueue
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation.conversation.conversation.ConversationFragment
import com.tla.uchattut.presentation.library.view.LibraryFragment
import com.tla.uchattut.presentation.profile.view.ProfileFragment
import com.tla.uchattut.presentation.schedule.view.ScheduleFragment
import com.tla.uchattut.presentation.tasks.view.TasksFragment
import kotlinx.android.synthetic.main.fragment_main.*
import java.util.*

class MainFragment : BaseFragment() {

    private val tabsQueue: Queue<Int> = UniqueQueue<Int>()
    private var visibleFragmentTag: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        initTabFragments()
        openFragment(ConversationFragment.TAG)
        tabsQueue.offer(nav_view.menu.getItem(0).itemId)

        nav_view.setOnNavigationItemSelectedListener {
            if (it.isChecked) return@setOnNavigationItemSelectedListener false

            tabsQueue.offer(it.itemId)
            when (it.itemId) {
                R.id.navigation_conversation -> {
                    openFragment(ConversationFragment.TAG)
                }
                R.id.navigation_library -> {
                    openFragment(LibraryFragment.TAG)
                }
                R.id.navigation_schedule -> {
                    openFragment(ScheduleFragment.TAG)
                }
                R.id.navigation_tasks -> {
                    openFragment(TasksFragment.TAG)
                }
                R.id.navigation_profile -> {
                    openFragment(ProfileFragment.TAG)
                }
            }

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun initTabFragments() {
        val conversationFragment = ConversationFragment()
        val libraryFragment = LibraryFragment()
        val scheduleFragment = ScheduleFragment()
        val tasksFragment = TasksFragment()
        val profileFragment = ProfileFragment()

        childFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, conversationFragment, ConversationFragment.TAG)
            .hide(conversationFragment)
            .add(R.id.mainFragmentContainer, libraryFragment, LibraryFragment.TAG)
            .hide(libraryFragment)
            .add(R.id.mainFragmentContainer, scheduleFragment, ScheduleFragment.TAG)
            .hide(scheduleFragment)
            .add(R.id.mainFragmentContainer, tasksFragment, TasksFragment.TAG)
            .hide(tasksFragment)
            .add(R.id.mainFragmentContainer, profileFragment, ProfileFragment.TAG)
            .hide(profileFragment)
            .commitNow()
    }

    private fun openFragment(tag: String) {
        val fragment = childFragmentManager.findFragmentByTag(tag)!!
        val transaction = childFragmentManager.beginTransaction()
        if (visibleFragmentTag != null) {
            val visibleFragment = childFragmentManager.findFragmentByTag(visibleFragmentTag)!!
            transaction.hide(visibleFragment)
        }

        transaction
            .show(fragment)
            .commit()

        visibleFragmentTag = tag
    }

    override fun onBackPressed(): Boolean {
        if(!super.onBackPressed() && tabsQueue.size > 1) {
            tabsQueue.poll()
            nav_view.selectedItemId = tabsQueue.peek()!!
            return true
        }
        return false
    }
}