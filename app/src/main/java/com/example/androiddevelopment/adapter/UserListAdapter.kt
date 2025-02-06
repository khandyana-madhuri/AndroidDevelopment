package com.example.androiddevelopment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddevelopment.data.model.User
import com.example.androiddevelopment.databinding.UserItemBinding



class UserListAdapter: RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    var userList: List<User> = emptyList()

    fun setUsers(users: List<User>) {
        userList = users
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding = UserItemBinding.inflate(layout, parent, false)
        return UserViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.binding.userId.text = user.id.toString()
        holder.binding.userTitle.text = user.username
        holder.binding.userBody.text = user.email
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root)

}