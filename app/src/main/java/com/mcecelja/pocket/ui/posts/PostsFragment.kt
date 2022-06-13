package com.mcecelja.pocket.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import com.mcecelja.pocket.R
import com.mcecelja.pocket.adapters.posts.PostAdapter
import com.mcecelja.pocket.databinding.FragmentPostsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsFragment : Fragment() {

    private lateinit var postsFragmentBinding: FragmentPostsBinding

    private val postsViewModel by viewModel<PostsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        postsFragmentBinding = FragmentPostsBinding.inflate(inflater, container, false)

        postsViewModel.setCategories()
        postsViewModel.setPosts()

        setupRecyclerView()

        postsViewModel.posts.observe(
            viewLifecycleOwner,
            { (postsFragmentBinding.rvPosts.adapter as PostAdapter).refreshData(it) })

        postsViewModel.categories.observe(
            viewLifecycleOwner,
            { it ->
                val adapter =
                    ArrayAdapter(requireContext(), R.layout.category_item, it.map { it.name })
                (postsFragmentBinding.actvCategory as? AutoCompleteTextView)?.setAdapter(adapter)
            })

        postsViewModel.titleFilter.observe(
            viewLifecycleOwner,
            { postsViewModel.setPosts() })

        postsViewModel.categoryFilter.observe(
            viewLifecycleOwner,
            { postsViewModel.setPosts() })

        postsFragmentBinding.etSearch.addTextChangedListener { postsViewModel.setTitleFilter(it.toString()) }
        postsFragmentBinding.actvCategory.addTextChangedListener {

            if(it.isNullOrBlank()) {
                postsFragmentBinding.tilCategory.endIconMode = TextInputLayout.END_ICON_DROPDOWN_MENU
                postsFragmentBinding.tilCategory.isEndIconVisible = true
            } else {
                postsFragmentBinding.tilCategory.endIconMode = TextInputLayout.END_ICON_CLEAR_TEXT
            }

            postsViewModel.setCategoryFilter(it.toString())
        }

        return postsFragmentBinding.root
    }

    private fun setupRecyclerView() {
        postsFragmentBinding.rvPosts.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        postsFragmentBinding.rvPosts.adapter =
            PostAdapter(postsViewModel.posts.value ?: mutableListOf())
    }


    companion object {
        const val TAG = "Posts"
        fun create(): PostsFragment {
            return PostsFragment()
        }
    }
}