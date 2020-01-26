package com.tla.uchattut.presentation.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation._common.viewModel
import javax.inject.Inject

class LibraryFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModel<LibraryViewModel>(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.libraryComponent(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_library, container, false)
    }
}