package com.example.mp_pr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class MyPageFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)
        val viewPager: ViewPager = view.findViewById(R.id.viewpager)
        val tabLayout: TabLayout = view.findViewById(R.id.tablayout)

        val adapter = MyFragmentPagerAdapter(childFragmentManager)
        adapter.addFragment(Fragment_A(), "내가 쓴 글")
        adapter.addFragment(Fragment_B(), "내 댓글")
        adapter.addFragment(Fragment_C(), "북마크")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        return view
    }
}