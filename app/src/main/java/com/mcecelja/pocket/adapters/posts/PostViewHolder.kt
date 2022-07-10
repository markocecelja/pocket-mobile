package com.mcecelja.pocket.adapters.posts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mcecelja.pocket.data.dto.post.PostDTO
import com.mcecelja.pocket.databinding.ItemPostBinding
import com.mcecelja.pocket.listener.PostItemClickListener

class PostViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(post: PostDTO, postItemClickListener: PostItemClickListener) {

        val itemBinding = ItemPostBinding.bind(itemView)
        itemBinding.tvPost.text = post.title
        itemBinding.tvCategory.text = post.category.name

        itemView.setOnClickListener { postItemClickListener.onPostClicked(post) }
    }
}