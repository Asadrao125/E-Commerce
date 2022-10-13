package com.kalsoft.e_commerce.helper

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.models.Product
import com.squareup.picasso.Picasso
import com.technado.demoapp.models.CategoriesModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class Commons {
    companion object {

        var TotalAmount: Double = 0.0
        var ShippingAmount: Double = 0.0
        var CartCount: Int = 0
        var FavoriteCount: Int = 0

        fun LoadImage(url: String, view: ImageView) {
            Picasso.get().load(url).placeholder(R.drawable.ic_launcher_background).into(view)
        }

        fun LoadImage2(url: String, view: ImageView) {
            Picasso.get().load(url).placeholder(R.drawable.ic_user_profile).into(view)
        }

        fun Toast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        fun ColorsList(context: Context): Int {
            val colorList: ArrayList<Int> = ArrayList()
            colorList.add(R.color.clr1)
            colorList.add(R.color.clr2)
            colorList.add(R.color.clr3)
            val r = Random()
            return colorList.get(r.nextInt(colorList.size))
        }

        fun getCategoriesList(): ArrayList<CategoriesModel> {
            val categoriesList = ArrayList<CategoriesModel>()
            categoriesList.clear()
            categoriesList.add(CategoriesModel("Mobiles", R.drawable.ic_mobile))
            categoriesList.add(CategoriesModel("T-Shirts", R.drawable.ic_shirt))
            categoriesList.add(CategoriesModel("Shoes", R.drawable.ic_shoes))
            categoriesList.add(CategoriesModel("Shorts", R.drawable.ic_shorts))
            categoriesList.add(CategoriesModel("Watch", R.drawable.ic_watch))
            return categoriesList
        }

        fun getSliderImagesList(): ArrayList<Int> {
            val imagesList = ArrayList<Int>()
            imagesList.add(R.drawable.slide1)
            imagesList.add(R.drawable.slide2)
            imagesList.add(R.drawable.slide3)
            return imagesList
        }

        fun LoadJSONFromAssets(context: Context, fileName: String): ArrayList<Product> {
            val productList: ArrayList<Product> = ArrayList()
            context.assets.open(fileName).bufferedReader().use { reader ->
                try {
                    val jsonObj = JSONObject(reader.readText())
                    val jsonArray: JSONArray = jsonObj.getJSONArray("products")
                    for (i in 0..jsonArray.length()) {
                        val jObject = jsonArray.getJSONObject(i)
                        val id = jObject.getString("id")
                        val title = jObject.getString("title")
                        val price = jObject.getDouble("price")
                        val url = jObject.getString("url")
                        productList.add(Product(id, 0, price, title, url, 0))
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            return productList
        }

    }
}