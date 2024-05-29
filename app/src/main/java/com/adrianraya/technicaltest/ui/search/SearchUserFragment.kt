package com.adrianraya.technicaltest.ui.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adrianraya.technicaltest.R
import com.adrianraya.technicaltest.databinding.FragmentSearchUserBinding
import com.adrianraya.technicaltest.ui.common.launchAndCollect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchUserFragment : Fragment(R.layout.fragment_search_user) {
    private val viewModel: SearchUserViewModel by viewModels()
    private lateinit var searchUserState: SearchUserState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchUserState = buildSearchUserState()

        FragmentSearchUserBinding.bind(view).apply {

            lyToolbar.tvTitleToolbar.text = getString(R.string.title_toolbar_search_user)
            lyToolbar.btBack.setOnClickListener {  findNavController().popBackStack() }

            viewLifecycleOwner.launchAndCollect(viewModel.state) {
                loading = it.loading
                user = it.user
                error = it.error?.let(searchUserState::errorToString)
            }
        }
        viewModel.onUiReady(1)
    }
}
