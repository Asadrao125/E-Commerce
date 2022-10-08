package com.kalsoft.e_commerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.activities.MainActivity
import com.kalsoft.e_commerce.fragments.ProductFragment
import com.kalsoft.e_commerce.helper.Commons
import com.technado.demoapp.models.CategoriesModel

class ProductAdapterHome(var context: MainActivity, var list: ArrayList<CategoriesModel>) :
    RecyclerView.Adapter<ProductAdapterHome.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_product_home, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categoriesModel: CategoriesModel = list.get(position)
        holder.imgProduct.setImageResource(categoriesModel.drawable)

        holder.mainLayout.setBackgroundColor(context.resources.getColor(Commons.ColorsList(context)))

        holder.itemView.setOnClickListener {
            context.replaceFragment(
                ProductFragment(),
                ProductFragment::class.java.simpleName,
                true,
                false
            )
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct: ImageView
        var imgFavorite: ImageView
        var mainLayout: RelativeLayout

        init {
            imgProduct = itemView.findViewById(R.id.imgProduct)
            imgFavorite = itemView.findViewById(R.id.imgFavorite)
            mainLayout = itemView.findViewById(R.id.mainLayout)
        }
    }
}