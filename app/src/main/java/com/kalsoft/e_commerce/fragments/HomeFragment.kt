package com.kalsoft.e_commerce.fragments

import android.app.Activity
import android.content.Context
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
import com.kalsoft.e_commerce.database.Database
import com.kalsoft.e_commerce.databinding.HomeFragmentBinding
import com.kalsoft.e_commerce.helper.Commons
import com.kalsoft.e_commerce.helper.Commons.Companion.getCategoriesList
import com.kalsoft.e_commerce.helper.Commons.Companion.getSliderImagesList
import com.kalsoft.e_commerce.helper.Titlebar
import com.kalsoft.e_commerce.models.Product
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : BaseFragment() {
    var binding: HomeFragmentBinding? = null
    var database: Database? = null
    var list: ArrayList<Product> = ArrayList()
    var dotscount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        getActivityContext()?.unlockMenu()
        getActivityContext?.showBttomBar()
        database = Database(getActivityContext!!)

        binding?.rvCategories?.layoutManager =
            LinearLayoutManager(getActivityContext!!, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvCategories?.setHasFixedSize(true)
        binding?.rvCategories?.adapter = CategoryAdapter(getActivityContext!!, getCategoriesList())

        setUpAdapter()

        setUpSlider(getSliderImagesList())

        return binding?.root
    }

    private fun setUpAdapter() {
        list.clear()
        list = Commons.LoadJSONFromAssets(getActivityContext!!, "products.json")

        binding?.rvProducts?.layoutManager = GridLayoutManager(getActivityContext!!, 2)
        binding?.rvProducts?.setHasFixedSize(true)
        binding?.rvProducts?.adapter = ProductAdapterHome(getActivityContext!!, list)
    }

    override fun onResume() {
        super.onResume()
        setUpAdapter()
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