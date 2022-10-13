package com.kalsoft.e_commerce.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.kalsoft.e_commerce.BaseFragment
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.databinding.SearchFragmentBinding
import com.kalsoft.e_commerce.helper.Titlebar

class SearchFragment : BaseFragment() {
    var binding: SearchFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)

        getActivityContext()?.lockMenu()
        getActivityContext?.hideBttomBar()

        return binding?.root
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setBackTitle(getActivityContext!!, "Search", 1)
        titlebar.binding
    }
}