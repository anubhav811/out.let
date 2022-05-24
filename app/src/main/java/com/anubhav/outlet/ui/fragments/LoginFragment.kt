package com.anubhav.outlet.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.anubhav.outlet.R
import com.anubhav.outlet.databinding.FragmentLoginBinding
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
import io.realm.mongodb.auth.GoogleAuthType


class LoginFragment : Fragment() {

    val appID = "outlet-ufgcz"

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Realm.init(context)
        var user: User? = null
        val app: App = App(
            AppConfiguration.Builder(appID)
                .build()
        )

        binding.btnSubmit.setOnClickListener {
            if(binding.etEmail.text.isEmpty() || binding.etPassword.text.isEmpty()){
                Toast.makeText(context,"Please enter all the fields",Toast.LENGTH_SHORT).show()
            }else{
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val credentials = Credentials.emailPassword(email, password)
            app.loginAsync(credentials) { result ->
                if (result.isSuccess) {
                    user = result.get()
                    Log.d("LoginFragment", "User logged in successfully")
                    Toast.makeText(context, "User logged in successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("LoginFragment", "Login failed")
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }}
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

            val signInIntent = gsinClient.signInIntent
            startActivityForResult(signInIntent, 5555)
        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 5555) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val authCode = account!!.idToken

                // Show signed-un UI

                // TODO(developer): send code to server and exchange for access/refresh/ID tokens
                val googleCredentials = Credentials.google(authCode, GoogleAuthType.ID_TOKEN)
                val app = App(AppConfiguration.Builder("outlet-ufgcz").build())
                app.loginAsync(googleCredentials) { it ->
                    if (it.isSuccess) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        Log.v("LoginFragment", "Successfully authenticated using Google OAuth.")
                        val user = app.currentUser()
                    } else {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                        Log.e("LoginFragment", it.error.toString())
                    }
                }
            } catch (e: ApiException) {
                Log.w("LoginFragment", "Sign-in failed", e)
            }

        }
    }

}