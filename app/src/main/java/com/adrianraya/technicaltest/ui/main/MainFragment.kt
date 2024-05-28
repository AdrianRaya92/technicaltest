package com.adrianraya.technicaltest.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.adrianraya.technicaltest.R
import com.adrianraya.technicaltest.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
