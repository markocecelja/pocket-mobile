package com.mcecelja.pocket.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mcecelja.pocket.R
import com.mcecelja.pocket.data.dto.post.PostDTO
import com.mcecelja.pocket.databinding.FragmentPostBinding
import com.mcecelja.pocket.ui.chat.ChatFragment
import com.mcecelja.pocket.ui.chat.MessagesFragment
import com.mcecelja.pocket.utils.ErrorUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostFragment : Fragment() {

    private lateinit var postFragmentBinding: FragmentPostBinding

    private val postViewModel by viewModel<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postFragmentBinding = FragmentPostBinding.inflate(inflater, container, false)

        val post = arguments?.getSerializable(KEY_POST) as PostDTO
        post.let {
            postViewModel.setPost(post)
        }

        postViewModel.post.observe(
            viewLifecycleOwner,
            { setupPost(it) })

        postFragmentBinding.mbContact.setOnClickListener { openChat() }

        return postFragmentBinding.root
    }

    private fun setupPost(post: PostDTO) {
        postFragmentBinding.tvPostTitle.text = post.title
        postFragmentBinding.tvPostDescription.text = post.description
        postFragmentBinding.tvCategory.text = post.category.name
    }

    private fun openChat() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.fl_fragmentContainer,
                MessagesFragment.create(postViewModel.post.value!!),
                MessagesFragment.TAG
            )
            .addToBackStack(ChatFragment.TAG)
            .commit()
    }

    companion object {
        const val TAG = "Post"
        private const val KEY_POST = "post"

        fun create(post: PostDTO): PostFragment {
            val args = Bundle()
            args.putSerializable(KEY_POST, post)

            val fragment = PostFragment()
            fragment.arguments = args

            return fragment
        }
    }
}