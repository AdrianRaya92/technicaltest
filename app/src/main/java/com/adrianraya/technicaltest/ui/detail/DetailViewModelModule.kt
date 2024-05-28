package com.adrianraya.technicaltest.ui.detail

import androidx.lifecycle.SavedStateHandle
import com.adrianraya.technicaltest.di.UserId
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @UserId
    fun provideUserId(savedStateHandle: SavedStateHandle) =
        UserDetailFragmentArgs.fromSavedStateHandle(savedStateHandle).userId
}