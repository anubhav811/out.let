package com.anubhav.outlet.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anubhav.outlet.R
import com.anubhav.outlet.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding : FragmentLoginBinding ? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentLoginBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

}