package com.dicoding.expert.ui.pages.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dicoding.expert.BuildConfig
import com.dicoding.expert.core.AppConst
import com.dicoding.expert.databinding.ActivitySplashBinding
import com.dicoding.expert.ui.pages.search.SearchActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvSplashVersion.text = BuildConfig.VERSION_NAME
        direct(SearchActivity::class.java)
    }

    private fun direct(cls: Class<*>) {
        val intent = Intent(this@SplashActivity, cls)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, AppConst.SPLASH_TIME_MILLISECOND)
    }
}