package com.kalsoft.e_commerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.activities.MainActivity
import com.kalsoft.e_commerce.database.Database
import com.kalsoft.e_commerce.fragments.ProductFragment
import com.kalsoft.e_commerce.helper.Commons
import com.kalsoft.e_commerce.models.Product
import com.technado.demoapp.models.CategoriesModel

class ProductAdapterHome(var context: MainActivity, var list: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapterHome.MyViewHolder>() {

    var database: Database? = Database(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_product_home, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product: Product = list.get(position)
        Commons.LoadImage(product.url, holder.imgProduct)

        holder.imgFavorite.setOnClickListener {
            if (!database?.isAdded(product.id.toInt())!!) {
                holder.imgFavorite.setImageResource(R.drawable.ic_favorite)
                product.isFavorite = 1
                product.quantity = 1
                database?.insertCategory(product)
            } else {
                if (database?.isFavoriteOrNot(product.id.toInt()) != 1) {
                    val updateIsFav = database?.updateIsFavorite(product.id.toInt(), 1)
                    if (updateIsFav!! > 0) {
                        Commons.Toast(context, "Added in Favorites")
                        holder.imgFavorite.setImageResource(R.drawable.ic_favorite)
                    } else {
                        Commons.Toast(context, "Failed to unfavorite")
                    }
                } else {
                    Commons.Toast(context, "Remove it from Favorites")
                }
            }
        }

        if (database?.isFavoriteExist(product.id.toInt())!! >= 1) {
            if (database?.isFavoriteOrNot(product.id.toInt()) == 1) {
                holder.imgFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                holder.imgFavorite.setImageResource(R.drawable.ic_favorite_outlined)
            }
        }

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
        //return list.size
        return 4
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