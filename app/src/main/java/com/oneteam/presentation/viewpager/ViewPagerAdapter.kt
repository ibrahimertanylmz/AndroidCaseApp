package com.oneteam.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oneteam.common.Constant
import com.oneteam.presentation.image.ImageFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val categories = listOf(Constant.CATEGORY_NATURE, Constant.CATEGORY_CITY, Constant.CATEGORY_ANIMAL)

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        return ImageFragment.newInstance(categories[position])
    }
}