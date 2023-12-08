package com.example.mp_pr.login;

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel(){
    var id : MutableLiveData<String> = MutableLiveData("");
    var password : MutableLiveData<String> = MutableLiveData("")

    var showInputNumberActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindIDActivity : MutableLiveData<Boolean> = MutableLiveData(false)
}


