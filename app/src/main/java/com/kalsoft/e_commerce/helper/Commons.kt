package com.kalsoft.e_commerce.helper

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.kalsoft.e_commerce.R
import com.squareup.picasso.Picasso
import com.technado.demoapp.models.CategoriesModel
import java.util.*
import kotlin.collections.ArrayList

class Commons {
    companion object {
        fun LoadImage(url: String, view: ImageView) {
            Picasso.get().load(url).placeholder(R.drawable.ic_launcher_background).into(view)
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

    }
}