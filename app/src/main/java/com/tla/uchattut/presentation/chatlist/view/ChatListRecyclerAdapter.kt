package com.tla.uchattut.presentation.chatlist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.chatlist.models.ChatRepoModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_list.*

class ChatListRecyclerAdapter(
    private val onItemClick: (id: Int) -> Unit = {}
) : RecyclerView.Adapter<ChatListRecyclerAdapter.ViewHolder>() {

    private val chatsList = arrayListOf<ChatRepoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_list, parent, false)
        return ViewHolder(view, onItemClick)
    }

    override fun getItemCount(): Int {
        return chatsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatsList[position])
    }

    fun setChatsList(chats: List<ChatRepoModel>) {
        chatsList.clear()
        chatsList.addAll(chats)
        notifyDataSetChanged()
    }

    class ViewHolder(
        override val containerView: View,
        private val onItemClick: (id: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(model: ChatRepoModel) {
            containerView.setOnClickListener { onItemClick(model.id) }
            usernameTextView.text = model.interlocutorName
            lastMessageTextView.text = model.lastMessage
            timeTextView.text = model.lastMessageTime

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
}