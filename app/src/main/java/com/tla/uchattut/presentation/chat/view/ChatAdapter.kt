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
import com.tla.uchattut.presentation.networking.ChatDialog

class ChatAdapter internal constructor(context: Context)
    : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var dialogs = emptyList<ChatDialog>()

    inner class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.dialogs_user_name)
        val lastMessage: TextView = itemView.findViewById(R.id.last_message)
        val userImage: ImageView = itemView.findViewById(R.id.dialogs_user_image)
        val messageTime: TextView = itemView.findViewById(R.id.dialogs_message_time)
        val unreadNumber: TextView = itemView.findViewById(R.id.dialogs_unread_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val itemView = inflater.inflate(R.layout.chat_item,parent,false)
        return ChatViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val current = dialogs[position]
        val context = holder.itemView.context
        holder.userName.text = current.userName
        holder.lastMessage.text = current.lastMessage
        holder.messageTime.text = current.messageTime
        holder.unreadNumber.text = current.unreadMsgNumber.toString()

        Glide.with(context)
            .load(current.imageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.userImage)



    }

    internal fun setDialogs(dialogs: List<ChatDialog>){
        this.dialogs = dialogs
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = dialogs.size
}