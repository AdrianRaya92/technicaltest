package com.adrianraya.technicaltest.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.adrianraya.technicaltest.R
import com.adrianraya.technicaltest.data.alert
import com.adrianraya.technicaltest.databinding.FragmentUsersListBinding
import com.adrianraya.technicaltest.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListFragment : Fragment(R.layout.fragment_users_list) {
    private val TAG = javaClass.simpleName
    private val viewModel: UsersListViewModel by viewModels()
    private lateinit var usersState: UsersState
    private val adapter = UsersListAdapter {
        usersState.onUserClicked(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            askBeforeClose()
        }
        usersState = buildUsersListState()

        FragmentUsersListBinding.bind(view).apply {
            rvUserList.adapter = adapter

            lyToolbar.btBack.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_close
                )
            )
            lyToolbar.tvTitleToolbar.text = getString(R.string.title_toolbar_user_list)
            lyToolbar.btBack.setOnClickListener { askBeforeClose() }

            viewLifecycleOwner.launchAndCollect(viewModel.state) {
                loading = it.loading
                users = it.users
                error = it.error?.let(usersState::errorToString)
                totalUsers = it.totalUsers
            }
        }
        viewModel.onUiReady()
    }

    private fun askBeforeClose() {
        alert {
            setTitle(R.string.app_name)
            setMessage(R.string.are_you_sure_question_close)
            setCancelable(false)
            setPositiveButton(R.string.common_yes) { _, _ ->
                requireActivity().finish()
            }
            setNegativeButton(R.string.common_no, null)
        }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
}
