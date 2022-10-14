package com.kalsoft.e_commerce.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kalsoft.e_commerce.BaseFragment
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.adapter.CartAdapter
import com.kalsoft.e_commerce.adapter.FavoriteAdapter
import com.kalsoft.e_commerce.database.Database
import com.kalsoft.e_commerce.databinding.FavoriteFragmentBinding
import com.kalsoft.e_commerce.databinding.ProfileFragmentBinding
import com.kalsoft.e_commerce.helper.Titlebar
import com.kalsoft.e_commerce.models.Product

class FavoriteFragment : BaseFragment() {
    var binding: FavoriteFragmentBinding? = null
    var database: Database? = null
    var productRecyclerView: RecyclerView? = null
    var list: ArrayList<Product> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)

        getActivityContext()?.lockMenu()
        getActivityContext?.showBttomBar()

        database = Database(getActivityContext!!)

        productRecyclerView = binding?.favRecyclerView
        productRecyclerView?.layoutManager = LinearLayoutManager(getActivityContext!!)
        productRecyclerView?.setHasFixedSize(true)
        list.clear()
        list = database?.getAllFavProducts(1)!!
        productRecyclerView?.adapter = FavoriteAdapter(getActivityContext!!, list)

        return binding?.root!!
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitle(getActivityContext!!, "Favorite", 1)
    }
}