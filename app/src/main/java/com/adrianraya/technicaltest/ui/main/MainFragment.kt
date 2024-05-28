package com.adrianraya.technicaltest.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.adrianraya.technicaltest.R
import com.adrianraya.technicaltest.data.alert
import com.adrianraya.technicaltest.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            askBeforeClose()
        }

        FragmentMainBinding.bind(view).apply {

            lyToolbar.btBack.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_close
                )
            )
            lyToolbar.btBack.setOnClickListener { askBeforeClose() }
            lyToolbar.tvTitleToolbar.text = getString(R.string.app_name)

            btnList.setOnClickListener { findNavController().navigate(R.id.action_main_to_list) }
            btnSearchUser.setOnClickListener { findNavController().navigate(R.id.action_main_to_search_user) }
        }
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
}
