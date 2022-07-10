package com.mcecelja.pocket.di

import com.mcecelja.pocket.ui.BaseViewModel
import com.mcecelja.pocket.ui.chat.ChatViewModel
import com.mcecelja.pocket.ui.chat.MessagesViewModel
import com.mcecelja.pocket.ui.login.LoginViewModel
import com.mcecelja.pocket.ui.pocket.PocketViewModel
import com.mcecelja.pocket.ui.post.PostViewModel
import com.mcecelja.pocket.ui.posts.PostsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
}

val viewModelModule = module {
    viewModel { BaseViewModel() }
    viewModel { LoginViewModel() }
    viewModel { PocketViewModel() }
    viewModel { MessagesViewModel() }
    viewModel { ChatViewModel() }
    viewModel { PostsViewModel() }
    viewModel { PostViewModel() }
}