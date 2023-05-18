package com.example.storyapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.logic.repository.preference.UserPreference
import com.example.storyapp.logic.repository.retrofit.responses.ListStoryItem
import com.example.storyapp.ui.ViewModelFactory
import com.example.storyapp.ui.add.AddStoryActivity
import com.example.storyapp.ui.detailstory.DetailStoryActivity
import com.example.storyapp.ui.map.MapsActivity
import com.example.storyapp.ui.welcome.OnboardActivity
import createToast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pref: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupAction()
        setupView()
        refreshApp()

    }

    private fun setupAction() {
        binding.fab.setOnClickListener{
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun setupView() {
        binding.rvStory.layoutManager = LinearLayoutManager(this)
        val listStoryAdapter = ListStoryAdapter2()

        binding.rvStory.adapter = listStoryAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                listStoryAdapter.retry()
            }
        )
        mainViewModel.getListStory.observe(this) {

            listStoryAdapter.submitData(lifecycle, it)
        }

        listStoryAdapter.setOnItemClickCallback(object : ListStoryAdapter2.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                val detailIntent = Intent(this@MainActivity, DetailStoryActivity::class.java)
                detailIntent.putExtra(DetailStoryActivity.story, data)
                startActivity(detailIntent)
            }
        })



    }

    private fun refreshApp() {
        binding.swipeToRefresh.setOnRefreshListener {
            setupView()
            binding.swipeToRefresh.isRefreshing = false
            createToast(this@MainActivity, getString(R.string.refresh))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.alert))
                setMessage(getString(R.string.yousure))
                setPositiveButton(R.string.logout) { _, _ ->
                    mainViewModel.logout()
                    startActivity(Intent(context, OnboardActivity::class.java))
                    finish()
                }
                create()
                show()
            }
        }else if(item.itemId == R.id.maps){
            startActivity(Intent(this@MainActivity, MapsActivity::class.java))
        }else if(item.itemId == R.id.localization){
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        return super.onOptionsItemSelected(item)
    }
}