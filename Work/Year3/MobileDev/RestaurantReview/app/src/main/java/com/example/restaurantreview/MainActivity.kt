package com.example.restaurantreview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView

import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenu
import com.google.android.material.navigation.NavigationView
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var myRef: DatabaseReference;
    private lateinit var firebaseFirestore : FirebaseFirestore
    private lateinit var restaurantList: ArrayList<RestaurantModel>
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var query: Query
    private lateinit var recyclerAdapter: RecyclerAdapter
    private lateinit var mContext: Context
    private lateinit var recyclerView: RecyclerView
    private lateinit var navigationView: NavigationView
    //private lateinit var searchView: SearchView
    private lateinit var userDisplayName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val database = Firebase.database

        //String mysting = getResources().getString(R.string.action_sign_in);
        //val list = res.getString(R.string.signup_fail)

        //Added auth stuff
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)


        navigationView = findViewById<NavigationView>(R.id.navigationView)


        val header: View = navigationView.getHeaderView(0)
        userDisplayName = header.findViewById<View>(R.id.userDisplayName) as TextView

        //searchView = findViewById<SearchView>(R.id.searchView)
        //val userName = FirebaseAuth.getInstance().currentUser?.displayName

        if (user != null) {

            val userName = FirebaseAuth.getInstance().currentUser?.email
            userDisplayName.text = userName

            //val name =  user.uid
            // val name =FirebaseDatabase.getInstance().getReference().child("Users") .child(user.getUid())
            //val name = user.email
            //Log.d("myTag", name.toString());
            //userDisplayName.text = name.toString()

            navigationView.menu.findItem(R.id.login).isVisible = false;
            navigationView.menu.findItem(R.id.logout).isVisible = true;
            navigationView.menu.findItem(R.id.review).isVisible = true;
            navigationView.menu.findItem(R.id.myReview).isVisible = true;
            navigationView.menu.findItem(R.id.favourites).isVisible = true;
            navigationView.menu.findItem(R.id.maps).isVisible = true;
        }

        navigationView.setNavigationItemSelectedListener(this)

        val toolbar = findViewById<Toolbar>(R.id.topAppBar)

       // setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.nav_draw_open,R.string.nav_draw_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.hasFixedSize()

        myRef = FirebaseDatabase.getInstance().reference

        restaurantList = ArrayList()
        recyclerAdapter = RecyclerAdapter(restaurantList, this)

        clearAll()

        getDataFromFirebase()

    }

    private fun getDataFromFirebase() {

        query = myRef.child("restaurants")

        query.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapShot: DataSnapshot in snapshot.children){
                    val restaurantModel = RestaurantModel()

                    restaurantModel.setImage(snapShot.child("image").getValue().toString())
                    restaurantModel.setNames(snapShot.child("name").getValue().toString())
                    restaurantModel.setLocation(snapShot.child("location").getValue().toString())


                    restaurantList.add(restaurantModel)
                }

                 recyclerAdapter = RecyclerAdapter(restaurantList, applicationContext)
                 recyclerView.adapter = recyclerAdapter
                 recyclerAdapter.notifyDataSetChanged()


            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })

       /*if(searchView!=null){
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{

                override fun onQueryTextSubmit(query: String): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onQueryTextChange(s: String): Boolean {
                    search(s)


                    return true
                    TODO("Not yet implemented")
                }

            })
        }*/
    }
    /*private fun search(s: String) {

        var myList : ArrayList<RestaurantModel> =  ArrayList()

        for (searchRest: RestaurantModel in restaurantList){

            if (searchRest.getDealDisc())
        }
    }*/

    private fun clearAll(){
        if (restaurantList != null){
            restaurantList.clear()

            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged()
            }
        }

        restaurantList = ArrayList()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.login -> startActivity(Intent(this@MainActivity, LoginPage::class.java))
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                finish()
                startActivity(intent)
                Toast.makeText(baseContext, R.string.logout,
                    Toast.LENGTH_SHORT).show()
            }
            R.id.review -> startActivity(Intent(this@MainActivity, Review::class.java))
        }
        return true
    }

    override fun onResume() {
        super.onResume()


        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {

            navigationView.menu.findItem(R.id.login).isVisible = false;
            navigationView.getMenu().findItem(R.id.logout).setVisible(true);
        }else {
            return
        }
    }

}