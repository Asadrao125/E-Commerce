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

class CartAdapter(var context: Context, var list: ArrayList<Product>) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    var database: Database? = Database(context)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val product: Product = list.get(position)
        Commons.LoadImage(product.url, holder.imgProduct)
        holder.tvTitle.setText(product.title)
        holder.tvCount.setText("" + product.quantity)

        holder.tvPrice.setText("" + product.price * product.quantity + " Rs")

        holder.imgPlus.setOnClickListener {
            val text: String = holder.tvCount.text.toString()
            var incCount: Int = text.toInt()
            incCount++
            product.quantity = incCount
            database?.updateProductQuantity(product.id.toInt(), incCount)
            holder.tvCount.setText("" + incCount)
        }

        holder.imgMinus.setOnClickListener {
            val text: String = holder.tvCount.text.toString()
            var decCount: Int = text.toInt()
            if (decCount > 1) {
                decCount--
                product.quantity = decCount
                database?.updateProductQuantity(product.id.toInt(), decCount)
                holder.tvCount.setText("" + decCount)
            } else {
                database?.deleteProduct(product.id.toInt())
                list.removeAt(position)
                notifyDataSetChanged()
            }
        }

        holder.imgCross.setOnClickListener {
            database?.deleteProduct(product.id.toInt())
            list.removeAt(position)
            notifyDataSetChanged()
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
        var imgMinus: ImageView
        var imgPlus: ImageView
        var imgCross: ImageView
        var tvCount: TextView

        init {
            imgProduct = itemView.findViewById(R.id.imgProduct)
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvPrice = itemView.findViewById(R.id.tvPrice)
            imgMinus = itemView.findViewById(R.id.imgMinus)
            imgPlus = itemView.findViewById(R.id.imgPlus)
            tvCount = itemView.findViewById(R.id.tvCount)
            imgCross = itemView.findViewById(R.id.imgCross)
        }
    }
}