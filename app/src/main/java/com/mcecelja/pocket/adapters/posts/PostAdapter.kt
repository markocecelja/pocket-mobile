package com.mcecelja.pocket.adapters.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mcecelja.pocket.R
import com.mcecelja.pocket.data.dto.post.PostDTO
import com.mcecelja.pocket.listener.PostItemClickListener

class PostAdapter(posts: List<PostDTO>, private val postItemClickListener: PostItemClickListener) : RecyclerView.Adapter<PostViewHolder>() {

    private val posts: MutableList<PostDTO> = mutableListOf()

    init {
        refreshData(posts)
    }

    fun refreshData(posts: List<PostDTO>) {
        this.posts.clear()
        this.posts.addAll(posts)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)

        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post, postItemClickListener)
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}