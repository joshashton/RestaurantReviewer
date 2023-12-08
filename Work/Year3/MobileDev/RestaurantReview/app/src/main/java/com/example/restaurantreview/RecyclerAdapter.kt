package com.example.restaurantreview

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder

class RecyclerAdapter(private val restaurantList: ArrayList<RestaurantModel> , private  var mContext: Context ): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerfill, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.setText(restaurantList.get(position).name)
        holder.location.setText(restaurantList.get(position).location)
       // var rate = holder.rating.getrestaurantList.get(position).rating)
        //holder.rating.rating(restaurantList.get(position).rating)

        //holder.imageView.setImageResource(
        Glide.with(mContext)
            .load(restaurantList.get(position).image).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<View>(R.id.recyclerImg) as ImageView
        var textView = itemView.findViewById<View>(R.id.recyclerName) as TextView
        var rating = itemView.findViewById<View>(R.id.ratingBar2) as RatingBar
        var location = itemView.findViewById<View>(R.id.location) as TextView

    }
}