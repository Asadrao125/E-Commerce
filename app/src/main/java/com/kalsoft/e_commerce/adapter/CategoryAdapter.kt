package com.kalsoft.e_commerce.adapter

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.activities.MainActivity
import com.kalsoft.e_commerce.fragments.LoginFragment
import com.kalsoft.e_commerce.fragments.ProductFragment
import com.kalsoft.e_commerce.helper.Commons
import com.technado.demoapp.models.CategoriesModel

class CategoryAdapter(var context: MainActivity, var list: ArrayList<CategoriesModel>) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.item_categories, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val categoriesModel: CategoriesModel = list.get(position)
        holder.imgCategory.setImageResource(categoriesModel.drawable)
        holder.tvCategoryName.setText(list.get(position).name)
        val background: Drawable = holder.layout_layout.getBackground()
        if (background is ShapeDrawable) {
            background.paint.color = ContextCompat.getColor(context, Commons.ColorsList(context))
        } else if (background is GradientDrawable) {
            background.setColor(ContextCompat.getColor(context, Commons.ColorsList(context)))
        } else if (background is ColorDrawable) {
            background.color = ContextCompat.getColor(context, Commons.ColorsList(context))
        }
        holder.itemView.setOnClickListener(View.OnClickListener {
            context.replaceFragment(
                ProductFragment(),
                ProductFragment::class.java.simpleName,
                true,
                false
            )
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgCategory: ImageView
        var layout_layout: RelativeLayout
        var tvCategoryName: TextView

        init {
            imgCategory = itemView.findViewById(R.id.imgCategory)
            layout_layout = itemView.findViewById(R.id.layout_layout)
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName)
        }
    }
}