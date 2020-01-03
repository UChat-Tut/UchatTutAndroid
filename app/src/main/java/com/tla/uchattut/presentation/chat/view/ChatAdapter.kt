package com.tla.uchattut.presentation.chat.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tla.uchattut.R
import com.tla.uchattut.presentation.chat.view.model.MessageModel

class ChatAdapter internal constructor(context: Context) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dialogs = emptyList<MessageModel>()

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userName: TextView = itemView.findViewById(R.id.dialogs_user_name)
        private val lastMessage: TextView = itemView.findViewById(R.id.last_message)
        private val userImage: ImageView = itemView.findViewById(R.id.dialogs_user_image)
        private val messageTime: TextView = itemView.findViewById(R.id.dialogs_message_time)
        private val unreadNumber: TextView = itemView.findViewById(R.id.dialogs_unread_number)

        fun bind(message: MessageModel) {
            userName.text = message.userName
            lastMessage.text = message.lastMessage
            messageTime.text = message.messageTime
            unreadNumber.text = message.unreadMsgNumber.toString()

            Glide.with(itemView.context)
                .load(message.imageUrl)
                .apply(RequestOptions.circleCropTransform())
                .into(userImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = inflater.inflate(R.layout.chat_item, parent, false)
        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(dialogs[position])
    }

    internal fun setDialogs(dialogs: List<MessageModel>) {
        this.dialogs = dialogs
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dialogs.size
}