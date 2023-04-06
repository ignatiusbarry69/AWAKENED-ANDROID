package com.example.hubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, login: String?) : FragmentStateAdapter(activity) {
    private var username = login
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()

        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.POSITION, position + 1)
            putString(FollowFragment.USERNAME, username)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}