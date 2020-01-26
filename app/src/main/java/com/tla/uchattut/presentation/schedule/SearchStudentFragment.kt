package com.tla.uchattut.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tla.uchattut.R
import com.tla.uchattut.presentation._common.BaseFragment

class SearchStudentFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_student, container, false)
    }

    companion object {
        const val TAG = "SearchStudentFragmentTag"
    }
}