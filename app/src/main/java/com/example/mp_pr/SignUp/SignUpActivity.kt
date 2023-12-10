package com.example.mp_pr.SignUp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mp_pr.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity(){
    private lateinit var auth : FirebaseAuth
    lateinit var signUpEmail: EditText
    lateinit var signUpPW : EditText
    lateinit var signUpPWCHECK : EditText
    lateinit var signUpBtn : Button
    private lateinit var signupPasswordCheck: EditText
    private lateinit var emailTextInputLayout: TextInputLayout
    private lateinit var passwordTextInputLayout: TextInputLayout
    private lateinit var confirmPasswordTextInputLayout: TextInputLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth
        signUpEmail = findViewById<EditText>(R.id.signup_email)
        signUpEmail.hint = "이메일"
        signUpPW = findViewById<EditText>(R.id.signup_password)
        signUpPW.hint = "비밀번호"
        signUpPWCHECK = findViewById<EditText>(R.id.signup_passwordcheck)
        signUpPWCHECK.hint = "비밀번호 확인"
        signUpBtn = findViewById<Button>(R.id.signUp_button)
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout)
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)
        confirmPasswordTextInputLayout = findViewById(R.id.confirmPasswordTextInputLayout)

        signUpEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val Email = s.toString()
                if(!isValidEmail(Email)){ emailTextInputLayout.error = "올바른 이메일 형식을 입력하세요" }
                else { emailTextInputLayout.error = null}
            }

            private fun isValidEmail(email: String): Boolean {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        })

        signUpPW.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val PW = s.toString()
                if (isValidPW(PW)) { passwordTextInputLayout.error = "비밀번호는 8자 이상이며, 숫자와 특수문자를 포함해야 합니다." }
                else { passwordTextInputLayout.error = null }
            }

            private fun isValidPW(PW: String): Boolean {
                return  PW.length >= 8 && PW.matches(".*\\d.*".toRegex()) && PW.matches(".*[!@#$%^&*()\\-_=+\\\\|\\[{\\]};:'\",<.>/?].*".toRegex())
            }
        })

        signUpPWCHECK.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val PWCHECK = s. toString()
                val PW = signUpPW.text.toString()
                if (PWCHECK != PW) { confirmPasswordTextInputLayout.error = "비밀번호가 일치하지 않습니다"}
                else { confirmPasswordTextInputLayout.error = null }
            }
        })


        signUpBtn.setOnClickListener {
            createAccount(signUpEmail.text.toString(), signUpPW.text.toString())

            val Email = signUpEmail.text.toString()
            val PW = signUpPW.text.toString()
            val PWCHECK = signUpPWCHECK.text.toString()

            if (Email.isEmpty() || PW.isEmpty() || PWCHECK.isEmpty()) {
                Toast.makeText(applicationContext, "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
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