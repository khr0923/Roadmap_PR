package com.example.mp_pr

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date

class WriteActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    var db = Firebase.firestore
    lateinit var write_title: EditText
    lateinit var write_content: EditText
    lateinit var write_btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        write_title = findViewById<EditText>(R.id.write_title)
        write_content = findViewById<EditText>(R.id.write_content)
        write_btn = findViewById<Button>(R.id.write_btn)

        auth = Firebase.auth

        write_btn.setOnClickListener {
            var contentModel = ContentModel()

            contentModel.uid = auth?.currentUser?.uid
            contentModel.com_title = write_title.text.toString()
            contentModel.com_content = write_content.text.toString()
            contentModel.timeStamp = System.currentTimeMillis()
            var now : Long = System.currentTimeMillis()
            var sdf = SimpleDateFormat("MM-dd HH:mm")
            var time_result = sdf.format(now)
            contentModel.str_timeStamp = time_result.toString()
            //contentModel.favoriteCount


            db.collection("test").add(contentModel).addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
                .addOnFailureListener{e ->
                    Log.w(TAG, "Error adding document", e)
                }

            setResult(Activity.RESULT_OK)

            finish()

        }


    }

}