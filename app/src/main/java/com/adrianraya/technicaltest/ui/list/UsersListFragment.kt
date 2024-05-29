package com.adrianraya.technicaltest.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adrianraya.technicaltest.R
import com.adrianraya.technicaltest.databinding.FragmentUsersListBinding
import com.adrianraya.technicaltest.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersListFragment : Fragment(R.layout.fragment_users_list) {
    private val viewModel: UsersListViewModel by viewModels()
    private lateinit var usersState: UsersState
    private val adapter = UsersListAdapter {
        usersState.onUserClicked(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersState = buildUsersListState()

        FragmentUsersListBinding.bind(view).apply {
            rvUserList.adapter = adapter

            lyToolbar.tvTitleToolbar.text = getString(R.string.title_toolbar_user_list)
            lyToolbar.btBack.setOnClickListener {  findNavController().popBackStack() }

            viewLifecycleOwner.launchAndCollect(viewModel.state) { uiState ->
                loading = uiState.loading
                users = uiState.users
                error = uiState.error?.let(usersState::errorToString)
                totalUsers = uiState.totalUsers
                uiState.users?.let { adapter.submitList(it) }
            }
        }
        viewModel.onUiReady()
    }
}
