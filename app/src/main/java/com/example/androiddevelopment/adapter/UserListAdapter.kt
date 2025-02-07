package com.example.androiddevelopment.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddevelopment.R
import com.example.androiddevelopment.data.model.User



class UserListAdapter: RecyclerView.Adapter<UserListAdapter.UserViewHolder>() {
    private var userList: List<User> = emptyList()

    fun setUsers(users: List<User>) {
        userList = users
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(layout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.apply {
            userID.text = user.id.toString()
            username.text = user.username
            email.text = user.email
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    inner class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val userID: TextView = itemView.findViewById(R.id.userId)
        val username: TextView = itemView.findViewById(R.id.userTitle)
        val email: TextView = itemView.findViewById(R.id.userBody)
    }

}