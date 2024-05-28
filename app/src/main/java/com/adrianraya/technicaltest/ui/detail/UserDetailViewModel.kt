package com.adrianraya.technicaltest.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianraya.technicaltest.di.UserId
import com.adrianraya.domain.Error
import com.adrianraya.domain.repositories.Users
import com.adrianraya.technicaltest.data.toError
import com.adrianraya.usecase.userdetail.FindUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    @UserId private val userId: Int,
    findUserUseCase: FindUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            findUserUseCase(userId)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { user -> _state.update { UiState(user = user) } }
        }
    }

    data class UiState(
        val user: Users? = null,
        val error: Error? = null
    )
}
