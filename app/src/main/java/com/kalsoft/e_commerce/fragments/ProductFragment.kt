package com.kalsoft.e_commerce.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kalsoft.e_commerce.BaseFragment
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.adapter.ProductAdapter
import com.kalsoft.e_commerce.database.Database
import com.kalsoft.e_commerce.databinding.ProductFragmentBinding
import com.kalsoft.e_commerce.helper.Titlebar
import com.kalsoft.e_commerce.models.Product
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ProductFragment : BaseFragment() {
    var binding: ProductFragmentBinding? = null
    var database: Database? = null
    var productRecyclerView: RecyclerView? = null
    var productsList: ArrayList<Product> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)

        getActivityContext()?.unlockMenu()
        getActivityContext?.showBttomBar()

        setAdapter()

        return binding?.root!!
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setTitle(getActivityContext!!, "Products", 0)
    }

    private fun setAdapter() {
        productsList.clear()
        productsList = getActivityContext!!.loadJSONFromAssets("products.json")
        productRecyclerView = binding?.productRecyclerView
        productRecyclerView?.layoutManager = GridLayoutManager(getActivityContext!!, 2)
        productRecyclerView?.setHasFixedSize(true)
        productRecyclerView?.adapter = ProductAdapter(getActivityContext!!, productsList)
    }

    fun Context.loadJSONFromAssets(fileName: String): ArrayList<Product> {
        applicationContext.assets.open(fileName).bufferedReader().use { reader ->
            try {
                val jsonObj = JSONObject(reader.readText())
                val jsonArray: JSONArray = jsonObj.getJSONArray("products")
                for (i in 0..jsonArray.length()) {
                    val jObject = jsonArray.getJSONObject(i)
                    val id = jObject.getString("id")
                    val title = jObject.getString("title")
                    val price = jObject.getDouble("price")
                    val url = jObject.getString("url")
                    productsList.add(Product(id, 0, price, title, url))
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return productsList
    }
}