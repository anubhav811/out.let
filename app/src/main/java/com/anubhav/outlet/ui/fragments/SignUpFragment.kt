package com.anubhav.outlet.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.anubhav.outlet.R
import com.anubhav.outlet.databinding.FragmentLoginBinding
import com.anubhav.outlet.databinding.FragmentSignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.User


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Realm.init(context)
        val appID = "outlet-ufgcz"
        var user: User? = null
        val app: App = App(
            AppConfiguration.Builder(appID)
                .build()
        )

        binding.btnSubmit.setOnClickListener {
            if (binding.etEmail.text.isEmpty() || binding.etPassword.text.isEmpty()) {
                Toast.makeText(context, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                app.emailPassword.registerUserAsync(email, password) { result ->
                    if (result.isSuccess) {
                        Log.d("SignUpFragment", "Account created successfully")
                        Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Log.d("SignUpFragment", "Sign Up failed")
                        Toast.makeText(context, "Sign Up failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        fun validateServerClientID() {
            val serverClientId = getString(R.string.server_client_id);
            val suffix = ".apps.googleusercontent.com";
            if (!serverClientId.trim().endsWith(suffix)) {
                val message = "Invalid server client ID in strings.xml, must end with $suffix";

                Log.w("LoginFragment", message);
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
            }
        }

        binding.googleBtn.setOnClickListener {
            validateServerClientID();

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build()

            val gsinClient = GoogleSignIn.getClient(requireContext(), gso)

            val intent = gsinClient.signInIntent
            startActivityForResult(intent, 1)

        }

    }

}
