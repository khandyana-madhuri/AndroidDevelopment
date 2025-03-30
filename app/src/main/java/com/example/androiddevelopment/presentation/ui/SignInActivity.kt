package com.example.androiddevelopment.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androiddevelopment.MainActivity
import com.example.androiddevelopment.R
import com.example.androiddevelopment.databinding.ActivitySignInBinding
import com.example.androiddevelopment.presentation.viewmodel.SignInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import org.koin.android.ext.android.inject

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val signInViewModel: SignInViewModel by inject()

    private val googleSignInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if(result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken ?: "")
            } catch(e: ApiException) {
                Log.e("Google SignIn", "SignIn failed ${e.statusCode}")
            }
        } else {
            if (result.resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        binding.viewModel = signInViewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser

        if(currentUser != null)
            openMainActivity()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInViewModel.signIn.observe(this) {
            signInWithGoogle()
        }
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    private fun signInWithGoogle() {
        binding.progressIndicator.visibility = View.VISIBLE
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("Google Sign-In", "Sign-in successful: ${user?.displayName}")
                    Toast.makeText(this, "Sign-in successful ${user?.displayName}", Toast.LENGTH_SHORT).show()
                    user?.let {
                        signInViewModel.handleGoogleSignInResult(user)
                        openMainActivity()
                        finish()
                        /*signInViewModel.getUser()
                        signInViewModel.user.observe(this) { users ->
                            if (users != null) {
                                println("SignInActivity Users data $users")
                            } else {
                                println("SignInActivity Users data empty")
                            }
                        }*/
                    }
                    binding.progressIndicator.visibility = View.GONE
                } else {
                    binding.progressIndicator.visibility = View.GONE
                    Log.e("Google Sign-In", "Sign-in failed", task.exception)
                }
            }
    }
}