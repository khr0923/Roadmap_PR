package com.example.mp_pr.SignUp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mp_pr.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity(){
    private lateinit var auth : FirebaseAuth
    lateinit var signUpEmail: EditText
    lateinit var signUpPW : EditText
    lateinit var signUpBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth
        signUpEmail = findViewById<EditText>(R.id.signup_email)
        signUpPW = findViewById<EditText>(R.id.signup_password)
        signUpBtn = findViewById<Button>(R.id.signUp_button)

        signUpBtn.setOnClickListener {
            createAccount(signUpEmail.text.toString(), signUpPW.text.toString())
        }


    }

    private fun createAccount(email: String, password: String) {
        if(email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {task ->
                    if(task.isSuccessful) {
                        Toast.makeText(
                            this, "회원가입 성공",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this, "회원가입 실패",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }
        }
    }
}