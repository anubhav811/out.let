package com.anubhav.outlet.ui.activities.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anubhav.outlet.databinding.FragmentLoginBinding
import com.anubhav.outlet.ui.activities.HomeActivity

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val submit = binding.appCompatButton

        submit.setOnClickListener {
            val intent = Intent(activity, HomeActivity::class.java).apply{}
            startActivity(intent)
        }
    }
}