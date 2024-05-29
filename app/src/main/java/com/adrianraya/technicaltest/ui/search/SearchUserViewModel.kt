package com.adrianraya.technicaltest.ui.search

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.adrianraya.domain.Error
import com.adrianraya.domain.repositories.UserDetail
import com.adrianraya.usecase.searchuser.RequestSearchUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchUserViewModel @Inject constructor(
    private val requestSearchUserUseCase: RequestSearchUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun onUiReady(userId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                userInfoVisibility = View.GONE
            )
            val (error, userDetail) = requestSearchUserUseCase(userId)
            _state.value = if (error == null) {
                _state.value.copy(
                    loading = false,
                    user = userDetail,
                    error = null,
                    userInfoVisibility = View.VISIBLE
                )
            } else {
                _state.value.copy(
                    loading = false,
                    error = error,
                    userInfoVisibility = View.GONE
                )
            }
        }
    }

    data class UiState(
        var loading: Boolean = false,
        val user: UserDetail? = null,
        val error: Error? = null,
        val userInfoVisibility: Int = View.GONE
    )
}