package com.kalsoft.e_commerce.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kalsoft.e_commerce.BaseFragment
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.adapter.CartAdapter
import com.kalsoft.e_commerce.database.Database
import com.kalsoft.e_commerce.databinding.CartFragmentBinding
import com.kalsoft.e_commerce.helper.Titlebar
import com.kalsoft.e_commerce.models.Product

class CartFragment : BaseFragment() {
    var binding: CartFragmentBinding? = null
    var database: Database? = null
    var productRecyclerView: RecyclerView? = null
    var list: ArrayList<Product> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false)

        getActivityContext()?.lockMenu()
        getActivityContext?.hideBttomBar()

        database = Database(getActivityContext!!)
        productRecyclerView = binding?.cartRecyclerView

        productRecyclerView?.layoutManager = LinearLayoutManager(getActivityContext!!)
        productRecyclerView?.setHasFixedSize(true)
        list.clear()
        list = database?.getAllProducts()!!
        productRecyclerView?.adapter = CartAdapter(getActivityContext!!, list)

        return binding?.root!!
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setBackTitle(getActivityContext!!, "Cart", 1)
    }

    fun calculateTotalAmount(list: ArrayList<Product>): Double {
        var sum = 0.0
        for (i in list) {
            sum = (sum + (i.price * i.quantity))
            Log.d("sum_bill_amount", "sum_bill_amount: $sum")
        }
        return sum
    }
}