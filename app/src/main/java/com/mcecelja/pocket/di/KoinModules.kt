package com.mcecelja.pocket.di

import com.mcecelja.pocket.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
}

val viewModelModule = module {
    viewModel { LoginViewModel() }
}