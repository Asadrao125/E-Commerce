package com.kalsoft.e_commerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.helper.Commons

class ViewPagerAdapter(var context: Context, var list: ArrayList<Int>) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater!!.inflate(R.layout.item_slider, null)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        imageView.setImageResource(list.get(position))
        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        val vp = container as ViewPager
        val view: View = obj as View
        vp.removeView(view)
    }

    init {
        this.context = context
    }
}