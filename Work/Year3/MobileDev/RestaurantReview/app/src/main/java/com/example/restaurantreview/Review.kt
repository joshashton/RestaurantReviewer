package com.example.restaurantreview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class Review : AppCompatActivity() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FirestoreRecyclerAdapter<*, *>
    val db= FirebaseFirestore.getInstance()

    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference;
    private lateinit var restaurantList: ArrayList<RestaurantModel>

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var query: Query

    private lateinit var mContext: Context

    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        recyclerView = findViewById<RecyclerView>(R.id.reviewRecycler)
        //firebaseFirestore = FirebaseFirestore.getInstance()

        //restaurantList = ArrayList()
        var query: Query = FirebaseFirestore.getInstance().collection("Restaurants")

        val options: FirestoreRecyclerOptions<RestaurantModel> = FirestoreRecyclerOptions.Builder<RestaurantModel>()
            .setQuery(query, RestaurantModel::class.java)
            .build()

        adapter=
            object : FirestoreRecyclerAdapter<RestaurantModel, RestaurantViewHolder?>(options) {
                override fun onBindViewHolder(
                    holder: RestaurantViewHolder,
                    position: Int,
                    model: RestaurantModel
                ) {
                    holder.textView.setText(model.name)
                    holder.location.setText(model.location)
                    //holder.imageView.setImageResource(
                    Glide.with(getApplicationContext())
                        .load(model.image).into(holder.imageView)

                    holder.itemView.setOnClickListener{

                        val restID :String = db.collection("Restaurants").document().id
                        val restName: String?  = holder.textView.text as String?
                        val intent = Intent(this@Review, ReviewPage::class.java)
                        intent.putExtra("restName", restName)
                        intent.putExtra("restID",restID)
                        finish()
                        startActivity(intent)
                    }
                }
                override fun onCreateViewHolder(group: ViewGroup, i: Int): RestaurantViewHolder  {
                    // Create a new instance of the ViewHolder, in this case we are using a custom
                    // layout called R.layout.message for each item
                    val view: View = LayoutInflater.from(group.context)
                        .inflate(R.layout.reviewrecyclerfill, group, false)
                    return RestaurantViewHolder(view)
                }
            }
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.hasFixedSize()
        recyclerView.setAdapter(adapter)

        /*linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.hasFixedSize()

        //firebase
        myRef = FirebaseDatabase.getInstance().reference

        restaurantList = ArrayList()
        recyclerAdapter = RecyclerAdapter2(restaurantList, this)

        //clearAll()

        //getDataFromFirebase()*/
    }

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var listener = itemView.setOnClickListener(View.OnClickListener {
            fun onClick(view: View){
                var position =  bindingAdapterPosition
            }
        }
        )

       // itemView

        var imageView =
            itemView.findViewById<View>(com.example.restaurantreview.R.id.recyclerImg) as ImageView
        var textView =
            itemView.findViewById<View>(com.example.restaurantreview.R.id.recyclerName) as TextView
        var card = itemView.findViewById<View>(com.example.restaurantreview.R.id.Card1) as CardView
        var location = itemView.findViewById<View>(com.example.restaurantreview.R.id.location) as TextView
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
}