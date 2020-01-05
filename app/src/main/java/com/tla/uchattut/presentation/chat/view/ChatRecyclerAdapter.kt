package com.tla.uchattut.presentation.chat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import com.tla.uchattut.presentation.chat.view_model.model.MessagePresentationModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message_date.*
import kotlinx.android.synthetic.main.item_message_receive.*
import kotlinx.android.synthetic.main.item_message_send.*

class ChatRecyclerAdapter : RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder>() {

    private val messages = mutableListOf<MessagePresentationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.SEND.value -> {
                val view = inflater.inflate(R.layout.item_message_send, parent, false)
                ViewHolder.SendMessageViewHolder(view)
            }
            ViewType.RECEIVE.value -> {
                val view = inflater.inflate(R.layout.item_message_receive, parent, false)
                ViewHolder.ReceiveMessageViewHolder(view)
            }
            ViewType.DATE.value -> {
                val view = inflater.inflate(R.layout.item_message_date, parent, false)
                ViewHolder.DateViewHolder(view)
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

    override fun getItemViewType(position: Int): Int =
        when (messages[position].type) {
            MessagePresentationModel.Type.DATE -> ViewType.DATE
            MessagePresentationModel.Type.OUT -> ViewType.SEND
            MessagePresentationModel.Type.IN -> ViewType.RECEIVE
        }.value

    fun setMessages(messages: List<MessagePresentationModel>) {
        this.messages.clear()
        this.messages.addAll(messages)
        notifyDataSetChanged()
    }

    enum class ViewType(val value: Int) {
        SEND(0), RECEIVE(1), DATE(2)
    }

    sealed class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        abstract fun bind(message: MessagePresentationModel)

        class DateViewHolder(view: View) : ViewHolder(view) {
            override fun bind(message: MessagePresentationModel) {
                dateTextView.text = message.time
            }
        }

        class SendMessageViewHolder(view: View) : ViewHolder(view) {
            override fun bind(message: MessagePresentationModel) {
                sendMessageTextView.text = message.text
                sendTimeTextView.text = message.time

                if (message.isLastMessageInBlock == true) {
                    sendCornerImageView.visibility = View.VISIBLE
                } else {
                    sendCornerImageView.visibility = View.GONE
                }
            }
        }

        class ReceiveMessageViewHolder(view: View) : ViewHolder(view) {
            override fun bind(message: MessagePresentationModel) {
                receiveMessageTextView.text = message.text
                receiveTimeTextView.text = message.time

                if (message.isLastMessageInBlock == true) {
                    receiveCornerImageView.visibility = View.VISIBLE
                } else {
                    receiveCornerImageView.visibility = View.GONE
                }
            }
        }
    }
}