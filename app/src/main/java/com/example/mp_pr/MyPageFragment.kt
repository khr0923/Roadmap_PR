package com.example.mp_pr

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.example.mp_pr.SignUp.SignUpActivity
import com.example.mp_pr.login.LogInActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment(){
    private  lateinit var logOutBtn : Button
    private  lateinit var signOutBtn : Button
    //private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)
        val viewPager: ViewPager = view.findViewById(R.id.viewpager)
        val tabLayout: TabLayout = view.findViewById(R.id.tablayout)
        logOutBtn = view.findViewById<Button>(R.id.LogOut_btn)
        signOutBtn = view.findViewById<Button>(R.id.SignOut_btn)
        //auth = Firebase.auth


        val adapter = MyFragmentPagerAdapter(childFragmentManager)
        adapter.addFragment(MyContentFragment(), "내가 쓴 글")
        adapter.addFragment(MyCommentFragment(), "내 댓글")
        adapter.addFragment(Fragment_C(), "북마크")

        logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LogInActivity::class.java))
        }

        signOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.delete()
            startActivity(Intent(activity, LogInActivity::class.java))

        }


        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        return view
    }
}