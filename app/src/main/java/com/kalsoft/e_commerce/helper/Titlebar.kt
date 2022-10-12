package com.kalsoft.e_commerce.helper

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.activities.MainActivity
import com.kalsoft.e_commerce.databinding.Titlebarbinding
import com.kalsoft.e_commerce.fragments.SearchFragment
import kotlinx.android.synthetic.main.titlebar.view.*

class Titlebar : RelativeLayout {
    var binding: Titlebarbinding? = null

    constructor(context: Context) : super(context) {
        initLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initLayout(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout(context)
    }

    fun initLayout(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.titlebar, this, true)
    }

    fun hideTitleBar() {
        resetTitlebar()
    }

    fun resetTitlebar() {
        binding?.rlTitlebarMainLayout?.setVisibility(View.GONE)
    }

    fun setBtnLeft(drawable: Int, listener: OnClickListener?) {
        binding?.ivBack!!.setImageResource(drawable)
        binding?.ivBack!!.setOnClickListener(listener)
    }

    fun setTitle(getActivityContext: MainActivity, title: String, showTitle: Int) {
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)
        binding?.tvTitle?.text = title
        binding?.ivBack?.visibility = View.GONE
        binding?.ivSearch?.visibility = View.VISIBLE
        binding?.ivMenu?.visibility = View.VISIBLE

        if (showTitle == 1) {
            binding?.tvTitle?.visibility = View.VISIBLE
        } else {
            binding?.tvTitle?.visibility = View.GONE
        }

        binding?.ivMenu?.setOnClickListener {
            getActivityContext.openDrawer()
        }

        binding?.ivSearch?.setOnClickListener {
            getActivityContext.replaceFragment(
                SearchFragment(),
                SearchFragment::class.java.simpleName, true, true
            )
        }
    }

    fun setBackTitle(getActivityContext: MainActivity, title: String, showTitle: Int) {
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)
        binding?.tvTitle?.text = title
        binding?.ivBack?.visibility = View.VISIBLE
        binding?.ivSearch?.visibility = View.VISIBLE
        binding?.ivMenu?.visibility = View.GONE

        if (showTitle == 1) {
            binding?.tvTitle?.visibility = View.VISIBLE
        } else {
            binding?.tvTitle?.visibility = View.GONE
        }

        binding?.ivBack?.setOnClickListener {
            getActivityContext.onBackPressed()
        }
    }

    fun setHideTitle() {
        resetTitlebar()
        binding?.rlTitlebarMainLayout?.setVisibility(View.VISIBLE)
        binding?.ivBack?.visibility = View.INVISIBLE
    }
}