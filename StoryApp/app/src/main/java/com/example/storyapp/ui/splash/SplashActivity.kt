package com.example.storyapp.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.example.storyapp.databinding.ActivitySplashBinding
import com.example.storyapp.ui.ViewModelFactory
import com.example.storyapp.ui.main.MainActivity
import com.example.storyapp.ui.welcome.OnboardActivity

class SplashActivity : AppCompatActivity(){
    private lateinit var binding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val factory: ViewModelFactory = ViewModelFactory.getInstance(applicationContext)
        val splashViewModel: SplashViewModel by viewModels(){factory}


        splashViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                skadoosh(intent)

            } else {
                val intent = Intent(this@SplashActivity, OnboardActivity::class.java)
                skadoosh(intent)
            }
        }
    }

    private fun skadoosh(intent: Intent){
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            finish()
        }, 1888)

    }

}