package com.tla.uchattut.presentation.conversation.search_user.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tla.uchattut.R
import com.tla.uchattut.data.network.model.MinimalUserNetworkModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_search_user.*

class UsersRecyclerAdapter : RecyclerView.Adapter<UsersRecyclerAdapter.UsersViewHolder>() {

    private var users: List<MinimalUserNetworkModel> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_search_user, parent, false)
        return UsersViewHolder(view)
    }

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(users[position])
    }

    fun setUsers(users: List<MinimalUserNetworkModel>) {
        this.users = users
        notifyDataSetChanged()
    }

    class UsersViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        @SuppressLint("SetTextI18n")
        fun bind(users: MinimalUserNetworkModel) {
            fullNameTextView.text = "${users.name} ${users.surname}"
        }
    }
}