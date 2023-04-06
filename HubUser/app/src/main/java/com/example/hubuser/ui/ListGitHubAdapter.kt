package com.example.hubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hubuser.databinding.ItemUserBinding
import com.example.hubuser.response.ItemsItem

class ListGitHubAdapter(private val listGithubUser: ArrayList<ItemsItem>) :
    RecyclerView.Adapter<ListGitHubAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val githubUser = listGithubUser[position]

        Glide.with(holder.itemView.context)
            .load(githubUser.avatarUrl)
            .circleCrop()
            .into(holder.imgAvatar)

        holder.tvUsername.text = githubUser.login

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listGithubUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listGithubUser.size

    inner class ListViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgAvatar: ImageView = binding.avatarHero
        val tvUsername: TextView = binding.nameHero

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}