package com.adrianraya.technicaltest.ui.search

import android.content.Context
import androidx.fragment.app.Fragment
import com.adrianraya.domain.Error
import com.adrianraya.technicaltest.R

fun Fragment.buildSearchUserState(
    context: Context = requireContext(),
) = SearchUserState(context)

class SearchUserState(
    private val context: Context,
) {

    fun errorToString(error: Error) = when (error) {
        Error.Connectivity -> context.getString(R.string.connectivity_error)
        is Error.Server -> context.getString(R.string.server_error) + error.code
        is Error.Unknown -> context.getString(R.string.unknown_error) + error.message
    }
}
