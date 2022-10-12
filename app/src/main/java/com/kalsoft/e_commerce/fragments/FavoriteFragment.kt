package com.kalsoft.e_commerce.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.kalsoft.e_commerce.BaseFragment
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.databinding.FavoriteFragmentBinding
import com.kalsoft.e_commerce.databinding.ProfileFragmentBinding
import com.kalsoft.e_commerce.helper.Titlebar

class FavoriteFragment : BaseFragment() {
    var binding: FavoriteFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)

        getActivityContext()?.lockMenu()
        getActivityContext?.hideBttomBar()

        return binding?.root!!
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setBackTitle(getActivityContext!!, "Favorite", 0)
    }
}