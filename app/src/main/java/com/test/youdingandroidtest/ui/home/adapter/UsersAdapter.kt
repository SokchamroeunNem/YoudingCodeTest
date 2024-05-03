package com.test.youdingandroidtest.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.youdingandroidtest.database.UserEntity
import com.test.youdingandroidtest.databinding.UserItemBinding

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private lateinit var binding: UserItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun setData(item: UserEntity) {
            binding.apply {
                userName.text = item.fullName
                userEmail.text = item.email
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.userId == newItem.userId
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    interface OnNoteDeletedListener {
        fun onNoteDeleted(note: UserEntity)
        fun onNoteCheckBox(note: UserEntity, status: Boolean)
    }

    interface OnNoteCheckBoxListener {
        fun onNoteCheckBox(note: UserEntity, status: Boolean)
    }

}