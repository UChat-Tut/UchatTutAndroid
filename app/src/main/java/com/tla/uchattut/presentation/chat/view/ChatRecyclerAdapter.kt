package com.tla.uchattut.presentation.chat.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import com.tla.uchattut.presentation._common.ActionModeSelectedItemsDelegate
import com.tla.uchattut.presentation._common.px
import com.tla.uchattut.presentation.chat.view_model.model.MessagePresentationModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message_date.*
import kotlinx.android.synthetic.main.item_message_receive.*
import kotlinx.android.synthetic.main.item_message_send.*

class ChatRecyclerAdapter(
    private val onItemClick: (id: Int) -> Unit = {},
    private val actionModeDelegate: ActionModeSelectedItemsDelegate<MessagePresentationModel>
) : RecyclerView.Adapter<ChatRecyclerAdapter.ViewHolder>() {

    private val messages = mutableListOf<MessagePresentationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.SEND.value -> {
                val view = inflater.inflate(R.layout.item_message_send, parent, false)
                ViewHolder.SendMessageViewHolder(view, onItemClick, actionModeDelegate)
            }
            ViewType.RECEIVE.value -> {
                val view = inflater.inflate(R.layout.item_message_receive, parent, false)
                ViewHolder.ReceiveMessageViewHolder(view, onItemClick, actionModeDelegate)
            }
            ViewType.DATE.value -> {
                val view = inflater.inflate(R.layout.item_message_date, parent, false)
                ViewHolder.DateViewHolder(view, onItemClick, actionModeDelegate)
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

    fun addMessage(message: MessagePresentationModel) {
        this.messages.add(message)
        notifyDataSetChanged()
    }

    fun getSelectedMessages(): List<MessagePresentationModel> =
        actionModeDelegate.selectedItems

    enum class ViewType(val value: Int) {
        SEND(0), RECEIVE(1), DATE(2)
    }

    sealed class ViewHolder(
        override val containerView: View,
        private val onItemClick: (id: Int) -> Unit,
        private val actionModeDelegate: ActionModeSelectedItemsDelegate<MessagePresentationModel>
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        abstract fun bind(message: MessagePresentationModel)

        protected fun bindMessageItemClickListener(
            view: View,
            model: MessagePresentationModel
        ) {
            containerView.setOnClickListener {
                if (actionModeDelegate.isActive()) {
                    actionModeDelegate.clickItem(view, model)
                } else {
                    onItemClick(model.id)
                }
            }
            containerView.setOnLongClickListener {
                actionModeDelegate.startActionMode(containerView, R.menu.chat_action_mode)
                actionModeDelegate.clickItem(view, model)
                true
            }
        }

        class DateViewHolder(
            view: View,
            onItemClick: (id: Int) -> Unit,
            actionModeDelegate: ActionModeSelectedItemsDelegate<MessagePresentationModel>
        ) : ViewHolder(view, onItemClick, actionModeDelegate) {

            override fun bind(message: MessagePresentationModel) {
                dateTextView.text = message.time
            }
        }

        class SendMessageViewHolder(
            view: View,
            onItemClick: (id: Int) -> Unit,
            actionModeDelegate: ActionModeSelectedItemsDelegate<MessagePresentationModel>
        ) :
            ViewHolder(view, onItemClick, actionModeDelegate) {

            init {

                val toggleButton: ToggleButton = view.findViewById(R.id.toggleButton)
                toggleButton.isClickable = false

                actionModeDelegate.addActionModeClickListener(
                    view,
                    object : ActionModeSelectedItemsDelegate.OnActionModeClickListener {
                        override fun selectItem() {
                            view.isActivated = true

                            toggleButton.isChecked = true
                        }

                        override fun unSelectItem() {
                            view.isActivated = false

                            toggleButton.isChecked = false
                        }

                        override fun onOpenActionMode() {
                            super.onOpenActionMode()

                            val v: View = view.findViewById(R.id.margingControlView)
                            v.layoutParams =
                                (v.layoutParams as ConstraintLayout.LayoutParams).apply {
                                    marginStart = v.context.px(32f).toInt()
                                }
                        }

                        override fun onCloseActionMode() {
                            super.onCloseActionMode()

                            val v: View = view.findViewById(R.id.margingControlView)
                            v.layoutParams =
                                (v.layoutParams as ConstraintLayout.LayoutParams).apply {
                                    marginStart = v.context.px(0f).toInt()
                                }
                        }
                    })
            }

            override fun bind(message: MessagePresentationModel) {
                sendMessageTextView.text = message.text
                sendTimeTextView.text = message.time

                if (message.isLastMessageInBlock == true) {
                    sendCornerImageView.visibility = View.VISIBLE
                } else {
                    sendCornerImageView.visibility = View.GONE
                }

                bindMessageItemClickListener(sendRootItem, message)
            }
        }

        class ReceiveMessageViewHolder(
            view: View,
            onItemClick: (id: Int) -> Unit,
            actionModeDelegate: ActionModeSelectedItemsDelegate<MessagePresentationModel>
        ) :
            ViewHolder(view, onItemClick, actionModeDelegate) {

            init {
                val toggleButton: ToggleButton = view.findViewById(R.id.toggleButton)
                toggleButton.isClickable = false

                actionModeDelegate.addActionModeClickListener(
                    view,
                    object : ActionModeSelectedItemsDelegate.OnActionModeClickListener {
                        override fun selectItem() {
                            view.isActivated = true
                            toggleButton.isChecked = true
                        }

                        override fun unSelectItem() {
                            view.isActivated = false
                            toggleButton.isChecked = false
                        }

                        override fun onOpenActionMode() {
                            super.onOpenActionMode()

                            val v: View = view.findViewById(R.id.margingControlView)
                            v.layoutParams =
                                (v.layoutParams as ConstraintLayout.LayoutParams).apply {
                                    marginStart = v.context.px(32f).toInt()
                                }
                        }

                        override fun onCloseActionMode() {
                            super.onCloseActionMode()

                            val v: View = view.findViewById(R.id.margingControlView)
                            v.layoutParams =
                                (v.layoutParams as ConstraintLayout.LayoutParams).apply {
                                    marginStart = v.context.px(0f).toInt()
                                }
                        }
                    })
            }

            override fun bind(message: MessagePresentationModel) {
                receiveMessageTextView.text = message.text
                receiveTimeTextView.text = message.time

                if (message.isLastMessageInBlock == true) {
                    receiveCornerImageView.visibility = View.VISIBLE
                } else {
                    receiveCornerImageView.visibility = View.GONE
                }

                bindMessageItemClickListener(receiveRootItem, message)
            }
        }
    }
}