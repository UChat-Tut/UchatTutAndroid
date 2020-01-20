package com.tla.uchattut.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tla.uchattut.R
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation.conversation.conversation.ConversationFragment
import com.tla.uchattut.presentation.library.view.LibraryFragment
import com.tla.uchattut.presentation.profile.view.ProfileFragment
import com.tla.uchattut.presentation.schedule.view.ScheduleFragment
import com.tla.uchattut.presentation.tasks.view.TasksFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

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
        replaceScreen(ConversationFragment())

        nav_view.setOnNavigationItemSelectedListener {
            if (it.isChecked) return@setOnNavigationItemSelectedListener false

            when (it.itemId) {
                R.id.navigation_conversation -> {
                    replaceScreen(ConversationFragment())
                }
                R.id.navigation_library -> {
                    replaceScreen(LibraryFragment())
                }
                R.id.navigation_schedule -> {
                    replaceScreen(ScheduleFragment())
                }
                R.id.navigation_tasks -> {
                    replaceScreen(TasksFragment())
                }
                R.id.navigation_profile -> {
                    replaceScreen(ProfileFragment())
                }
            }

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun replaceScreen(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
            .addToBackStack(MAIN_FRAGMENT_BACK_STACK)
            .commit()
    }

    override fun onBackPressed(): Boolean {
        super.onBackPressed()
        return true
    }

    companion object {
        const val MAIN_FRAGMENT_BACK_STACK = "MainFragmentBackStack"
    }


}