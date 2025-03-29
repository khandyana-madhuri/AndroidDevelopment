package com.example.androiddevelopment.di

import com.example.androiddevelopment.presentation.viewmodel.ImageViewerViewModel
import com.example.androiddevelopment.presentation.viewmodel.PdfViewerViewModel
import com.example.androiddevelopment.presentation.viewmodel.SignInViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { ImageViewerViewModel() }
    viewModel { PdfViewerViewModel(get()) }
}