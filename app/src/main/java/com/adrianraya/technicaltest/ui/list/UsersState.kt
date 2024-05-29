package com.adrianraya.technicaltest.ui.list

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.adrianraya.domain.Error
import com.adrianraya.domain.repositories.Users
import com.adrianraya.technicaltest.R

fun Fragment.buildUsersListState(
    context: Context = requireContext(),
    navController: NavController = findNavController()
) = UsersState(context, navController)

class UsersState(
    private val context: Context,
    private val navController: NavController
) {

    fun onUserClicked(user: Users) {
        val action = UsersListFragmentDirections.actionListToDetail(user.id)
        navController.navigate(action)
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}
