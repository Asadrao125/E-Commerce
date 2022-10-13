package com.kalsoft.e_commerce.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.database.Database
import com.kalsoft.e_commerce.helper.Commons
import com.kalsoft.e_commerce.models.Product

class FavoriteAdapter(var context: Context, var list: ArrayList<Product>) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {
    var database: Database? = Database(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product: Product = list.get(position)
        Commons.LoadImage(product.url, holder.imgProduct)
        holder.tvTitle.setText(product.title)
        holder.tvPrice.setText("" + product.price + " Rs")

        holder.imgFavorite.setOnClickListener {
            val update = database?.updateIsFavorite(product.id.toInt(), 0)
            if (update!! >= 1) {
                list.removeAt(position)
                notifyDataSetChanged()
            } else {
                Commons.Toast(context, "Failed To Update")
            }
        }

        holder.imgCross.setOnClickListener {
            val update = database?.updateIsFavorite(product.id.toInt(), 0)
            if (update!! >= 1) {
                list.removeAt(position)
                notifyDataSetChanged()
            } else {
                Commons.Toast(context, "Failed To Update")
            }
        }
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgProduct: ImageView
        var tvTitle: TextView
        var tvPrice: TextView
        var imgCross: ImageView
        var imgFavorite: ImageView

        init {
            imgProduct = itemView.findViewById(R.id.imgProduct)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            imgCross = itemView.findViewById(R.id.imgCross)
            imgFavorite = itemView.findViewById(R.id.imgFavorite)
        }
    }
}