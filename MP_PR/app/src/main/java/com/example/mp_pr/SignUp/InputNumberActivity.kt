package com.example.mp_pr.SignUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mp_pr.R
import com.example.mp_pr.databinding.ActivityFindIdBinding
import com.example.mp_pr.databinding.ActivityInputNumberBinding

class InputNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInputNumberBinding // 수정
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputNumberBinding.inflate(layoutInflater) // 수정
        setContentView(binding.root)
    }
}