package com.javi_macbook.restaurapp.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.javi_macbook.restaurapp.R
import com.javi_macbook.restaurapp.model.Dish


class DishFragment : Fragment() {

    lateinit var root: View

    var dish: Dish? = null
        set(value){
            field = value
            // Accedemos a las vistas
            val dishImage = root.findViewById<ImageView>(R.id.dish_image)
            val dishName = root.findViewById<TextView>(R.id.dish_name)
            val dishDescription = root.findViewById<TextView>(R.id.dish_description)
            val dishPrice = root.findViewById<TextView>(R.id.dish_price)

            // Actualizo la vista con el modelo
            value?.let {
                dishImage.setImageResource(value.image)
                dishName.text = value.name
                dishDescription.text = value.description

                val priceString = getString(R.string.dish_price, value.price)
                dishPrice.text = priceString
            }
        }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        // Si inflater es distinto de null
        if (inflater != null) {

            root = inflater.inflate(R.layout.fragment_dish, container, false)
            dish = Dish("Porra Antequerana", R.drawable.porra,15.95f,"Plato antequerano típico","Ninguno")
        }

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

}