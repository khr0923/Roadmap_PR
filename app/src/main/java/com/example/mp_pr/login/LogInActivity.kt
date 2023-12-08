package com.example.mp_pr.login;

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mp_pr.MainActivity
import com.example.mp_pr.R
import com.example.mp_pr.SignUp.SignUpActivity
import com.example.mp_pr.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LogInActivity : AppCompatActivity() {
//    private lateinit var loginViewModel: LoginViewModel
//    private lateinit var binding: ActivityLogInBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        //setContentView(R.layout.activity_main)
//
//        binding = ActivityLogInBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        loginViewModel=ViewModelProvider(this)[LoginViewModel::class.java]
//        binding.viewModel = loginViewModel
//        binding.activity = this
//        binding.lifecycleOwner = this
//
//    }

    private lateinit var auth : FirebaseAuth
    lateinit var logInEmail: EditText
    lateinit var logInPW : EditText
    lateinit var LogInBtn : Button
    lateinit var SignUpButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        auth = Firebase.auth
        logInEmail = findViewById<EditText>(R.id.edittext_id)
        logInPW = findViewById<EditText>(R.id.edittext_password)
        LogInBtn = findViewById<Button>(R.id.SignInButton)
        SignUpButton = findViewById<Button>(R.id.SignUpButton)

        SignUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        LogInBtn.setOnClickListener {
            createAccount(logInEmail.text.toString(), logInPW.text.toString())
        }

    }

    private fun createAccount(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    if(task.isSuccessful) {
                        Toast.makeText(
                            this, "로그인 성공",
                            Toast.LENGTH_SHORT
                        ).show()
                        moveMainPage(auth.currentUser)
                    } else {
                        Toast.makeText(
                            this, "로그인 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        }
    }

    fun moveMainPage(user: FirebaseUser?){
        if( user!= null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}

