package com.example.hubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.commit
import com.example.hubuser.R
import com.example.hubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val homeFragment = SearchUserFragment()
        val fragment = fragmentManager.findFragmentByTag(SearchUserFragment::class.java.simpleName)

        if (fragment !is SearchUserFragment) {
            Log.d("Github User", "Fragment Name :" + SearchUserFragment::class.java.simpleName)
            fragmentManager.commit {
                add(R.id.frame_container, homeFragment, SearchUserFragment::class.java.simpleName)

            }
        }
    }
}