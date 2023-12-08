package com.example.restaurantreview

class RestaurantModel {

    var name: String? = null
    var image: String? = null
    var location: String? = null
    var rating : Int? =1

    constructor(name: String, image: String, location: String, rating : Int){
        this.name = name
        this.image = image
        this.location = location
        this.rating = rating

    }
    constructor(){

    }
    @JvmName("getImage1")
    fun getImage():String?{
        return image
    }
     @JvmName("setImageURL1")
     fun setImage (image: String ){
         this.image = image
     }

    fun setNames (name: String ){
        this.name = name
    }
    @JvmName("setLocation1")
    fun setLocation (location: String ){
        this.location = location
    }
}