package com.adrianraya.technicaltest.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adrianraya.technicaltest.R
import com.adrianraya.technicaltest.data.alert
import com.adrianraya.technicaltest.databinding.FragmentUserDetailBinding
import com.adrianraya.technicaltest.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private val viewModel: UserDetailViewModel by viewModels()
    private lateinit var userDetailState: UserDetailState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDetailState = buildUserDetailsState()

        FragmentUserDetailBinding.bind(view).apply {
            lyToolbar.btBack.setOnClickListener { findNavController().popBackStack() }
            lyToolbar.tvTitleToolbar.text = getString(R.string.title_toolbar_user_detail)

            viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
                user = state.user
                state.error?.let { error ->
                    alert {
                        state.error.toString()
                        setMessage(error.let(userDetailState::errorToString))
                        setPositiveButton(android.R.string.ok) { _, _ -> }
                    }.show()
                }
            }
        }
    }
}