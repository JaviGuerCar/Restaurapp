package com.javi_macbook.restaurapp.model

import android.widget.ImageView
import java.io.Serializable

data class Dish (val name: String, val image: Int, val price: Float, val description: String, val alergens: List<String> = listOf()) : Serializable {


}