package com.tla.uchattut.presentation.chatlist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.chatlist.models.ChatRepoModel
import com.tla.uchattut.presentation._common.ActionModeSelectItemsDelegate
import com.tla.uchattut.presentation._common.PrimaryActionModeCallback
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_list.*
import java.util.*

class ChatListRecyclerAdapter(
    private val onItemClick: (id: Int) -> Unit = {},
    onActionItemClickListener: PrimaryActionModeCallback.OnActionModeClickListener
) : RecyclerView.Adapter<ChatListRecyclerAdapter.ViewHolder>(), Filterable {

    private val chatsList = arrayListOf<ChatRepoModel>()
    private val chatsFilteredList = arrayListOf<ChatRepoModel>()
    private val actionModeDelegate =
        ActionModeSelectItemsDelegate<ChatRepoModel>(onActionItemClickListener)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return ViewHolder(view, onItemClick, actionModeDelegate)
    }

    override fun getItemCount(): Int {
        return chatsFilteredList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatsFilteredList[position])
    }

    fun setChats(chats: List<ChatRepoModel>) {
        setChatsList(chats)
        setFilteredChatsList(chats)

        notifyDataSetChanged()
    }

    class ViewHolder(
        override val containerView: View,
        private val onItemClick: (id: Int) -> Unit,
        private val actionModeDelegate: ActionModeSelectItemsDelegate<ChatRepoModel>
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(model: ChatRepoModel) {
            containerView.setOnClickListener {
                if (actionModeDelegate.isActive()) {
                    actionModeDelegate.clickItem(rootItem, model)
                } else {
                    onItemClick(model.id)
                }
            }
            containerView.setOnLongClickListener {
                actionModeDelegate.startActionMode(containerView, R.menu.chat_list_action_mode)
                actionModeDelegate.clickItem(rootItem, model)
                true
            }
            usernameTextView.text = model.interlocutorName
            lastMessageTextView.text = model.lastMessage
            sendTimeTextView.text = model.lastMessageTime

            if (model.unreadMessageCount != null) {
                unreadCountTextView.visibility = View.GONE
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
            val filteredList = mutableListOf<ChatRepoModel>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(chatsList)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                val filteredPatternList = chatsList.filter {
                    it.interlocutorName.toLowerCase(Locale.getDefault()).trim().contains(filterPattern)
                }
                filteredList.addAll(filteredPatternList)
            }
            val filterResults = FilterResults()
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            setFilteredChatsList(results!!.values as List<ChatRepoModel>)

            notifyDataSetChanged()
        }
    }

    private fun setChatsList(chats: List<ChatRepoModel>) {
        chatsList.clear()
        chatsList.addAll(chats)
    }

    private fun setFilteredChatsList(chats: List<ChatRepoModel>) {
        chatsFilteredList.clear()
        chatsFilteredList.addAll(chats)
    }

    override fun getFilter(): Filter = listFilter
}