package com.tla.uchattut.presentation.dialogues.view

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
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.*
import com.tla.uchattut.presentation.dialogues.view_model.DialoguesViewModel
import kotlinx.android.synthetic.main.fragment_chat_list.*
import javax.inject.Inject

class DialoguesFragment : Fragment() {

    @Inject
    lateinit var viewModel: DialoguesViewModel

    private lateinit var mainNavController: NavController

    private lateinit var chatListAdapter: DialoguesRecyclerAdapter

    private val onActionItemClickListener =
        object : PrimaryActionModeCallback.OnActionModeMenuClickListener {
            override fun onMenuItemSelected(item: MenuItem) {
                when (item.itemId) {
                    R.id.muteItem -> onMuteItemClick()
                    R.id.deleteItem -> onDeleteItemClick()
                }
            }
        }

    private val actionModeDelegate =
        ActionModeSelectedItemsDelegate<DialogueRepoModel>(onActionItemClickListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        DaggerContainer.dialoguesComponent(this)
            .inject(this)
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

        chatListAdapter = DialoguesRecyclerAdapter(
            onItemClick = ::openChat,
            actionModeDelegate = actionModeDelegate
        )

        val dividerDrawable = resources.getDrawable(R.drawable.divider_horizontal, activity!!.theme)
        chatListRecyclerView.layoutManager = LinearLayoutManager(context!!)
        chatListRecyclerView.adapter = chatListAdapter
        chatListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                dividerDrawable
            )
        )

        viewModel.chatList.observe(viewLifecycleOwner, Observer<List<DialogueRepoModel>> {
            chatListAdapter.setChats(it)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            updateState(state)
        })
    }

    private fun openChat(id: Int) {
        val action = MobileNavDirections.actionToChatFragment(id = id)
        mainNavController.navigate(action)
    }

    private fun updateState(state: DialoguesViewModel.State) =
        when (state) {
            DialoguesViewModel.State.LOADING -> {
                loadingLayout.visibility = View.VISIBLE
                contentLayout.visibility = View.GONE
                emptyLayout.visibility = View.GONE
            }
            DialoguesViewModel.State.CONTENT -> {
                loadingLayout.visibility = View.GONE
                contentLayout.visibility = View.VISIBLE
                emptyLayout.visibility = View.GONE
            }
            DialoguesViewModel.State.EMPTY -> {
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

    private fun onMuteItemClick() {
        toast("Отключены оповещания")
        actionModeDelegate.finishActionMode()
    }

    private fun onDeleteItemClick() {
        toast("Удаление")
        actionModeDelegate.finishActionMode()
    }

    private fun addDialog() {
        toast("Add")
    }
}