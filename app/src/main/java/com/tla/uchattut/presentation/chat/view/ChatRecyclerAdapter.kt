package com.tla.uchattut.presentation.chat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import com.tla.uchattut.data.repositories.chat.models.MessageRepoModel
import kotlinx.android.extensions.LayoutContainer

class ChatRecyclerAdapter : RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder>() {

    private val messages = mutableListOf<MessageRepoModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.SEND.value -> {
                val view = inflater.inflate(R.layout.item_send_message, parent, false)
                ViewHolder.SendMessageViewHolder(view)
            }
            ViewType.RECEIVE.value -> {
                val view = inflater.inflate(R.layout.item_receive_message, parent, false)
                ViewHolder.ReceiveMessageViewHolder(view)
            }
            else -> throw IllegalArgumentException("No viewHolder for viewType: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemViewType(position: Int): Int {
        return ViewType.SEND.value
    }

    fun setMessages(messages: List<MessageRepoModel>) {
        this.messages.clear()
        this.messages.addAll(messages)
        notifyDataSetChanged()
    }

    enum class ViewType(val value: Int) {
        SEND(0), RECEIVE(1)
    }

    sealed class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        abstract fun bind(message: MessageRepoModel)

        class SendMessageViewHolder(view: View) : ViewHolder(view) {
            override fun bind(message: MessageRepoModel) {

            }
        }

        class ReceiveMessageViewHolder(view: View) : ViewHolder(view) {
            override fun bind(message: MessageRepoModel) {

            }
        }
    }
}