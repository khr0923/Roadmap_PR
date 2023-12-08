package com.example.mp_pr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.mp_pr.databinding.ActivityMainBinding

private const val TAG_ROADMAP = "roadmap_fragment"
private const val TAG_HOME = "home_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replaceFragment(HomeFragment())

        binding.navigationView.setOnItemSelectedListener { MenuItem ->
            when (MenuItem.itemId) {
                R.id.home -> replaceFragment(HomeFragment())
                R.id.roadmap -> replaceFragment(RoadmapFragment())
                R.id.myPage -> replaceFragment(MyPageFragment())

            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.mainFrameLayout,fragment)
                commit()
            }
    }

}
