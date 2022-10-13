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

class ProductAdapter(var context: Context, var list: ArrayList<Product>) :
    RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {
    var database: Database? = Database(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product: Product = list.get(position)
        Commons.LoadImage(product.url, holder.imgProduct)
        holder.tvPrice.setText("" + product.price + " Rs")

        holder.imgAdd.setOnClickListener {
            product.quantity = 1
            val isInserted: Long = database?.insertCategory(product)!!
            if (isInserted > 0) {
                holder.tvTitle.setText(product.title + " - Added")
                holder.imgAdd.isEnabled = false
            } else {
                Commons.Toast(context, "Failed to add")
            }
        }

        if (database?.isAdded(product.id.toInt())!!) {
            holder.imgAdd.isEnabled = false
            holder.tvTitle.setText(product.title + " - Added")
        } else {
            holder.imgAdd.isEnabled = true
            holder.tvTitle.setText(product.title)
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
        var imgAdd: ImageView
        var item_cv: CardView

        init {
            imgProduct = itemView.findViewById(R.id.imgProduct)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            item_cv = itemView.findViewById(R.id.item_cv)
            imgAdd = itemView.findViewById(R.id.imgAdd)
        }
    }
}