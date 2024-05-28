package com.adrianraya.technicaltest.ui.list

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.adrianraya.domain.Error
import com.adrianraya.domain.repositories.Users
import com.adrianraya.technicaltest.R
import kotlinx.coroutines.CoroutineScope

fun Fragment.buildUsersListState(
    context: Context = requireContext(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController()
) = UsersState(context, scope, navController)

class UsersState(
    private val context: Context,
    private val scope: CoroutineScope,
    private val navController: NavController
) {

    fun onUserClicked(user: Users) {
        /*
        val action = ComicsFragmentDirections.actionMainToDetail(comic.id)
        navController.navigate(action)
         */
    }

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}
