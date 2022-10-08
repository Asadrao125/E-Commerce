package com.kalsoft.e_commerce.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.kalsoft.e_commerce.BaseFragment
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.adapter.CategoryAdapter
import com.kalsoft.e_commerce.adapter.ProductAdapterHome
import com.kalsoft.e_commerce.adapter.ViewPagerAdapter
import com.kalsoft.e_commerce.databinding.HomeFragmentBinding
import com.kalsoft.e_commerce.helper.Titlebar
import com.technado.demoapp.models.CategoriesModel
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {
    var binding: HomeFragmentBinding? = null
    var dotscount = 0
    var categoriesList: ArrayList<CategoriesModel>? = ArrayList()
    var imagesList: ArrayList<Int>? = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        getActivityContext()?.unlockMenu()
        getActivityContext?.showBttomBar()

        categoriesList?.clear()
        imagesList?.clear()
        categoriesList?.add(CategoriesModel("Mobiles", R.drawable.ic_mobile))
        categoriesList?.add(CategoriesModel("T-Shirts", R.drawable.ic_shirt))
        categoriesList?.add(CategoriesModel("Shoes", R.drawable.ic_shoes))
        categoriesList?.add(CategoriesModel("Shorts", R.drawable.ic_shorts))
        categoriesList?.add(CategoriesModel("Watch", R.drawable.ic_watch))

        imagesList?.add(R.drawable.slide1)
        imagesList?.add(R.drawable.slide2)
        imagesList?.add(R.drawable.slide3)

        binding?.rvCategories?.layoutManager =
            LinearLayoutManager(getActivityContext!!, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvCategories?.setHasFixedSize(true)
        binding?.rvCategories?.adapter = CategoryAdapter(getActivityContext!!, categoriesList!!)

        binding?.rvProducts?.layoutManager = GridLayoutManager(getActivityContext!!, 2)
        binding?.rvProducts?.setHasFixedSize(true)
        binding?.rvProducts?.adapter = ProductAdapterHome(getActivityContext!!, categoriesList!!)

        setUpSlider(imagesList)

        return binding?.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitle(getActivityContext!!, "Home", 0)
    }

    private fun setUpSlider(imagesList: ArrayList<Int>?) {
        val viewPagerAdapter = ViewPagerAdapter(getActivityContext!!, imagesList!!)
        binding?.viewPager!!.adapter = viewPagerAdapter

        val timer = Timer()
        timer.scheduleAtFixedRate(
            The_slide_timer(getActivityContext!!, binding?.viewPager!!, imagesList),
            2000,
            3000
        )

        dotscount = viewPagerAdapter.getCount()
        val dots = arrayOfNulls<ImageView>(dotscount)

        for (i in 0 until dotscount) {
            dots[i] = ImageView(getActivityContext!!)
            dots[i]!!.setImageDrawable(
                ContextCompat.getDrawable(
                    getActivityContext!!,
                    R.drawable.non_active_dot
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            binding?.SliderDots?.addView(dots[i], params)
        }

        dots[0]!!.setImageDrawable(
            ContextCompat.getDrawable(
                getActivityContext!!,
                R.drawable.active_dot
            )
        )

        binding?.viewPager!!.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                for (i in 0 until dotscount) {
                    dots[i]!!.setImageDrawable(
                        ContextCompat.getDrawable(
                            getActivityContext!!,
                            R.drawable.non_active_dot
                        )
                    )
                }
                dots[position]!!.setImageDrawable(
                    ContextCompat.getDrawable(
                        getActivityContext!!,
                        R.drawable.active_dot
                    )
                )
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    class The_slide_timer(
        var activity: Activity,
        var viewPager: ViewPager,
        var imagesList: ArrayList<Int>?
    ) : TimerTask() {
        override fun run() {
            activity.runOnUiThread(Runnable {
                if (viewPager.getCurrentItem() < imagesList!!.size - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1)
                } else viewPager.setCurrentItem(0)
            })
        }
    }
}