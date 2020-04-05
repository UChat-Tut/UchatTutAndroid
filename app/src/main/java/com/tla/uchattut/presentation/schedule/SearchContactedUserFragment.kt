package com.tla.uchattut.presentation.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.R
import com.tla.uchattut.data.network.model.UserNetworkModel
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.main.MainActivity
import com.tla.uchattut.presentation.schedule.adapters.FoundStudentsAdapter
import kotlinx.android.synthetic.main.fragment_search_student.*
import javax.inject.Inject

class SearchContactedUserFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val searchViewModel by lazy {
        viewModel<SearchContactedUserViewModel>(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerContainer.searchContactedUserComponent(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FoundStudentsAdapter(::onStudentClicked)
        foundStudentRecyclerView.adapter = adapter
        foundStudentRecyclerView.layoutManager = LinearLayoutManager(context)

        searchViewModel.getContactedUsersLiveData().observe(viewLifecycleOwner, Observer {
            adapter.setStudents(it)
        })

        searchViewModel.requestContactedUsers()
        searchInput.addTextChangedListener {
            searchViewModel.requestContactedUsers(it.toString())
        }
    }

    private fun onStudentClicked(student: UserNetworkModel){
        (activity as MainActivity)._foundStudentLiveData.postValue(student)
        (activity as FragmentActivity).onBackPressed()
    }

    companion object {
        const val TAG = "SearchStudentFragmentTag"
    }
}
