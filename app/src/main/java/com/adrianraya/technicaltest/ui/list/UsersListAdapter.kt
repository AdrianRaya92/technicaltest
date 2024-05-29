package com.adrianraya.technicaltest.ui.list

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adrianraya.domain.repositories.Users
import com.adrianraya.technicaltest.R
import com.adrianraya.technicaltest.databinding.ItemUserBinding
import com.adrianraya.technicaltest.ui.common.basicDiffUtil
import com.adrianraya.technicaltest.ui.common.inflate

class UsersListAdapter(private val listener: (Users) -> Unit) :
    ListAdapter<Users, UsersListAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.item_user, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener { listener(user) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemUserBinding.bind(view)
        fun bind(user: Users) {
            binding.users = user
        }
    }
}