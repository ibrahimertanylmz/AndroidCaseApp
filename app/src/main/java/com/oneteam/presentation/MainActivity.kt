package com.oneteam.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.oneteam.app.databinding.ActivityMainBinding
import com.oneteam.presentation.viewpager.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var imageAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = imageAdapter
        TabLayoutMediator(binding.tablayout, binding.viewPager) { tab, position ->
            //Some implementation
        }.attach()


    }

    fun getViewPager(): ViewPager2 {
        return binding.viewPager
    }


}

