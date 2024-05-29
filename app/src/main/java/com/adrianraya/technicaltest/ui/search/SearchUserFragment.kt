package com.adrianraya.technicaltest.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
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
    private var userId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchUserState = buildSearchUserState()

        FragmentSearchUserBinding.bind(view).apply {

            lyToolbar.tvTitleToolbar.text = getString(R.string.title_toolbar_search_user)
            lyToolbar.btBack.setOnClickListener {  findNavController().popBackStack() }

            etIdUser.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    userId = if(etIdUser.text.toString().trim() == "") 0
                    else etIdUser.text.toString().trim().toInt()
                }
            })

            btnSearch.setOnClickListener { searchUser(userId) }

            viewLifecycleOwner.launchAndCollect(viewModel.state) {
                loading = it.loading
                user = it.user
                error = it.error?.let(searchUserState::errorToString)
                userInfoVisibility = it.userInfoVisibility
            }
        }
    }

    private fun searchUser(id: Int){
        hideKeyboard()
        viewModel.onUiReady(id)
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}
