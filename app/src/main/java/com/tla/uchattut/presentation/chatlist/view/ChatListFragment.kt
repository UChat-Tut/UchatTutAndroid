package com.tla.uchattut.presentation.chatlist.view

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.view.View.OnAttachStateChangeListener
import android.widget.ImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.MobileNavDirections
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.chatlist.models.ChatRepoModel
import com.tla.uchattut.presentation._common.DividerItemDecoration
import com.tla.uchattut.presentation._common.PrimaryActionModeCallback
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.chatlist.view_model.ChatListViewModel
import kotlinx.android.synthetic.main.fragment_chat_list.*

class ChatListFragment : Fragment() {

    private lateinit var mainNavController: NavController
    private val viewModel by lazy {
        viewModel { ChatListViewModel() }
    }

    private lateinit var chatListAdapter: ChatListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainNavController = Navigation.findNavController(activity!!, R.id.nav_host_fragment)

        chatListAdapter = ChatListRecyclerAdapter(
            onItemClick = { id -> openChat(id) },
            onActionItemClickListener = onActionItemClickListener
        )

        val dividerDrawable = resources.getDrawable(R.drawable.divider_horizontal, activity!!.theme)
        chatListRecyclerView.layoutManager = LinearLayoutManager(context!!)
        chatListRecyclerView.adapter = chatListAdapter
        chatListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                dividerDrawable
            )
        )

        viewModel.chatList.observe(viewLifecycleOwner, Observer<List<ChatRepoModel>> {
            chatListAdapter.setChats(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            updateState(state)
        })

        viewModel.requestChatList()
    }

    private fun openChat(id: Int) {
        val action = MobileNavDirections.actionToChatFragment(id = id)
        mainNavController.navigate(action)
    }

    private fun updateState(state: ChatListViewModel.State) =
        when (state) {
            ChatListViewModel.State.LOADING -> {
                loadingLayout.visibility = View.VISIBLE
                contentLayout.visibility = View.GONE
                emptyLayout.visibility = View.GONE
            }
            ChatListViewModel.State.CONTENT -> {
                loadingLayout.visibility = View.GONE
                contentLayout.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
            }
            ChatListViewModel.State.EMPTY -> {
                loadingLayout.visibility = View.GONE
                contentLayout.visibility = View.GONE
                emptyLayout.visibility = View.VISIBLE
            }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.chat_list, menu)

        val searchView: SearchView = menu.findItem(R.id.actionSearch).actionView as SearchView

        searchView.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(arg0: View?) {
                setItemsVisibility(menu, true)
            }

            override fun onViewAttachedToWindow(arg0: View?) {
                setItemsVisibility(menu, false)
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                chatListAdapter.filter.filter(newText)
                return false
            }
        })

        val addActionView: ImageButton = menu.findItem(R.id.actionAdd).actionView as ImageButton
        val addDialogMenu = createAddDialogPopupMenu(addActionView, this::onAddDialogClickListener)
        addActionView.run {
            setBackgroundColor(Color.TRANSPARENT)
            setImageResource(R.drawable.ic_add)
        }
        addActionView.setOnClickListener {
            addDialogMenu.show()
        }
    }

    private fun setItemsVisibility(menu: Menu, visible: Boolean) {
        val exception = menu.findItem(R.id.actionSearch)
        for (i in 0 until menu.size()) {
            val item = menu.getItem(i)
            if (item === exception) continue

            item.isVisible = visible
        }
    }

    private fun createAddDialogPopupMenu(
        view: View,
        onItemClick: (menuItem: MenuItem) -> Unit
    ): PopupMenu = PopupMenu(context!!, view).apply {
        menuInflater.inflate(R.menu.add_chat_dialog, menu)
        setOnMenuItemClickListener {
            onItemClick(it)
            true
        }
    }

    private fun onAddDialogClickListener(menuItem: MenuItem) {
        when (menuItem.itemId) {
            R.id.newMessageItem -> addDialog()
        }
    }

    private val onActionItemClickListener = object : PrimaryActionModeCallback.OnActionModeClickListener {
        override fun onMenuItemSelected(item: MenuItem) {
            when (item.itemId) {
                R.id.deleteItem -> toast("Удаление")
                R.id.muteItem -> toast("Отключены оповещания")
            }
        }

        override fun selectItem(view: View) {
            view.isActivated = true
        }

        override fun unSelectItem(view: View) {
            view.isActivated = false
        }
    }

    private fun addDialog() {
        toast("Add")
    }
}