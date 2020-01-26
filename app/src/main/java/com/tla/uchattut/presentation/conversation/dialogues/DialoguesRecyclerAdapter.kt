package com.tla.uchattut.presentation.conversation.dialogues

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import com.tla.uchattut.presentation._common.ActionModeSelectedItemsDelegate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_list.*
import java.util.*

class DialoguesRecyclerAdapter(
    private val onItemClick: (id: Int) -> Unit = {},
    private val actionModeDelegate: ActionModeSelectedItemsDelegate<DialogueRepoModel>
) : RecyclerView.Adapter<DialoguesRecyclerAdapter.ViewHolder>(), Filterable {

    private val chatsList = arrayListOf<DialogueRepoModel>()
    private val chatsFilteredList = arrayListOf<DialogueRepoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return ViewHolder(
            view,
            onItemClick,
            actionModeDelegate
        )
    }

    override fun getItemCount(): Int {
        return chatsFilteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatsFilteredList[position])
    }

    fun setChats(chats: List<DialogueRepoModel>) {
        setChatsList(chats)
        setFilteredChatsList(chats)

        notifyDataSetChanged()
    }

    class ViewHolder(
        override val containerView: View,
        private val onItemClick: (id: Int) -> Unit,
        private val actionModeDelegate: ActionModeSelectedItemsDelegate<DialogueRepoModel>
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            val rootView = containerView.rootView
            actionModeDelegate.addActionModeClickListener(
                rootView,
                object : ActionModeSelectedItemsDelegate.OnActionModeClickListener {
                    override fun selectItem() {
                        rootView.isActivated = true
                    }

                    override fun unSelectItem() {
                        rootView.isActivated = false
                    }
                }
            )
        }

        fun bind(model: DialogueRepoModel) {

            containerView.setOnClickListener {
                if (actionModeDelegate.isActive()) {
                    actionModeDelegate.clickItem(rootItem, model)
                } else {
                    onItemClick(model.id!!)
                }
            }
            containerView.setOnLongClickListener {
                actionModeDelegate.startActionMode(containerView, R.menu.chat_list_action_mode)
                actionModeDelegate.clickItem(rootItem, model)
                true
            }
            usernameTextView.text = model.name
            lastMessageTextView.text = model.lastMessage?.message ?: ""
            sendTimeTextView.text = model.lastMessage?.time ?: ""

            if (model.unreadMessageCount != null && model.unreadMessageCount!! > 0) {
                unreadCountTextView.visibility = View.VISIBLE
                unreadCountTextView.text = model.unreadMessageCount.toString()
            } else {
                unreadCountTextView.visibility = View.GONE
            }

            Glide.with(containerView)
                .load(model.imageUrl)
                .into(avatarImageView)
        }
    }

    private val listFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<DialogueRepoModel>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(chatsList)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                val filteredPatternList = chatsList.filter {
                    it.name?.toLowerCase(Locale.getDefault())?.trim()
                        ?.contains(filterPattern) == true
                }
                filteredList.addAll(filteredPatternList)
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            setFilteredChatsList(results!!.values as List<DialogueRepoModel>)

            notifyDataSetChanged()
        }
    }

    private fun setChatsList(chats: List<DialogueRepoModel>) {
        chatsList.clear()
        chatsList.addAll(chats)
    }

    private fun setFilteredChatsList(chats: List<DialogueRepoModel>) {
        chatsFilteredList.clear()
        chatsFilteredList.addAll(chats)
    }

    override fun getFilter(): Filter = listFilter
}