package com.tla.uchattut.presentation.conversation.search_user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.R
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.viewModel
import kotlinx.android.synthetic.main.fragment_search_user.*
import javax.inject.Inject

class SearchUserFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        viewModel<SearchUserViewModel>(viewModelFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DaggerContainer.searchUserComponent(this)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            UsersRecyclerAdapter()
        usersRecyclerView.adapter = adapter
        usersRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.getUsersLiveData().observe(viewLifecycleOwner, Observer {
            adapter.setUsers(it)
        })
    }

    fun executeSearchQuery(query: String?) {
        if (query == null) return
        viewModel.searchUsers(query)
    }
}