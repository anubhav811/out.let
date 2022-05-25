package com.anubhav.outlet.ui.fragments.auth

import android.app.Activity.RESULT_OK
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
import com.anubhav.outlet.ui.activities.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import io.realm.Realm
import io.realm.mongodb.App
import io.realm.mongodb.AppConfiguration
import io.realm.mongodb.Credentials
import io.realm.mongodb.auth.GoogleAuthType


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var app: App
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Realm.init(requireContext())
        app = App(AppConfiguration.Builder("outlet-ufgcz").build())


        binding.btSubmit.setOnClickListener {
            // get text from edit text
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val credentials = Credentials.emailPassword(email, password)
            app.loginAsync(credentials) {
                if (it.isSuccess) {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

         binding.btGoogle.setOnClickListener {
             validateServerClientID()

             val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                 .requestIdToken(getString(R.string.server_client_id))
                 .requestEmail()
                 .build()
             val gSinClient = GoogleSignIn.getClient(requireContext(), gso)
             val signInIntent: Intent = gSinClient.signInIntent
             startActivityForResult(signInIntent, 0, null)
         }

    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            if (requestCode == 0 && resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                val account = task.result
                val idToken = account?.idToken

                val credential = Credentials.google(idToken, GoogleAuthType.ID_TOKEN)
                app.loginAsync(credential) {
                    if (it.isSuccess) {
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }
    private fun validateServerClientID() {
        val serverClientId = getString(R.string.server_client_id)
        val suffix = ".apps.googleusercontent.com"
        if (!serverClientId.trim { it <= ' ' }.endsWith(suffix)) {
            val message =
                "Invalid server client ID in strings.xml, must end with $suffix"
            Log.w("LoginFragment", message)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

}