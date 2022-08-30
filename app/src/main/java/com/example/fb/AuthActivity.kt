package com.example.fb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var launcher: ActivityResultLauncher<Intent>
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        auth = Firebase.auth
        checkAuthUser()
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val userAccount = task.getResult(ApiException::class.java)
                if (userAccount != null) {
                    fireBaseAuthWithGoogle(userAccount.idToken!!)
                }
            } catch (e: ApiException) {
                Log.e("ApiException", "$e")
            }
        }
        val signIn: TextView = findViewById(R.id.sign_in)
        signIn.setOnClickListener {
            googleSignIn()
        }
    }

    private fun signIn(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    private fun googleSignIn() {
        val signInClient = signIn()
        launcher.launch(signInClient.signInIntent)
    }

    private fun fireBaseAuthWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    this,
                    "Регистрация прошла успешно",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, MainActivity::class.java))
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else Toast.makeText(this, "Не удалось зарегистрироваться", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAuthUser(){
        if (auth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}