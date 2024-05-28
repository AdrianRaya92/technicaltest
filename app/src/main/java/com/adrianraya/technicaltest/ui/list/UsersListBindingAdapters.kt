package com.adrianraya.technicaltest.ui.list

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.adrianraya.domain.repositories.Users

@BindingAdapter("items")
fun RecyclerView.setItems(users: List<Users>?) {
    if (users != null) {
        (adapter as? UsersListAdapter)?.submitList(users)
    }
}