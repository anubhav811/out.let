package com.anubhav.outlet.viewmodels;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anubhav.outlet.databinding.PostItemBinding

class PostViewHolder(
    private val binding : PostItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(){
        TODO()
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            val binding =
                PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostViewHolder(binding)
        }
    }

}
