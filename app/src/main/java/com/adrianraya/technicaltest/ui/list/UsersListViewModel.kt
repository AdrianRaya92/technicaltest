package com.adrianraya.technicaltest.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adrianraya.domain.repositories.Users
import dagger.hilt.android.lifecycle.HiltViewModel
import com.adrianraya.domain.Error
import com.adrianraya.technicaltest.data.toError
import com.adrianraya.usecase.userlist.GetUsersListTotalUseCase
import com.adrianraya.usecase.userlist.GetUsersListUseCase
import com.adrianraya.usecase.userlist.RequestUsersListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    getUsersListUseCase : GetUsersListUseCase,
    private val requestUsersListUseCase: RequestUsersListUseCase,
    private val getUsersListTotalUseCase: GetUsersListTotalUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getUsersListUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { users -> _state.update { it.copy(users = users) } }
        }
    }

    fun onUiReady() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true
            )
            val error = requestUsersListUseCase()
            if (error == null) {
                _state.value = _state.value.copy(
                    loading = false,
                    totalUsers = getUsersListTotalUseCase().toString()
                )
            } else {
                _state.value = _state.value.copy(loading = false, error = error)
            }

        }
    }

    data class UiState(
        var loading: Boolean = false,
        val users: List<Users>? = null,
        val error: Error? = null,
        val totalUsers: String = "0"
    )
}