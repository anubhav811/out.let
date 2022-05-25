package com.anubhav.outlet.ui.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.anubhav.outlet.data.entities.PostItem
import com.anubhav.outlet.viewmodels.PostViewHolder

class PostItemAdapter(
    private val context: Context
): ListAdapter<PostItem, PostViewHolder>(DIFF_CALLBACK) {

    companion object DIFF_CALLBACK: DiffUtil.ItemCallback<PostItem>(){
        override fun areItemsTheSame(oldItem: PostItem, newItem: PostItem): Boolean =
            TODO()

        override fun areContentsTheSame(oldItem: PostItem, newItem: PostItem): Boolean =
            TODO()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        TODO("Not yet implemented")
        PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        TODO("Not yet implemented")
        holder.bind()
    }


}