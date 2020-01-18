package com.tla.uchattut.presentation.conversation.dialogues.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.tla.uchattut.MobileNavDirections
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import com.tla.uchattut.di.DaggerContainer
import com.tla.uchattut.presentation._common.ActionModeSelectedItemsDelegate
import com.tla.uchattut.presentation._common.DividerItemDecoration
import com.tla.uchattut.presentation._common.PrimaryActionModeCallback
import com.tla.uchattut.presentation._common.toast
import com.tla.uchattut.presentation.conversation.dialogues.view_model.DialoguesViewModel
import kotlinx.android.synthetic.main.fragment_dialogues.*
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
                    R.id.removeItem -> onDeleteItemClick()
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
        return inflater.inflate(R.layout.fragment_dialogues, container, false)
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

    private fun onMuteItemClick() {
        toast("Отключены оповещания")
        actionModeDelegate.finishActionMode()
    }

    private fun onDeleteItemClick() {
        toast("Удаление")
        actionModeDelegate.finishActionMode()
    }
}