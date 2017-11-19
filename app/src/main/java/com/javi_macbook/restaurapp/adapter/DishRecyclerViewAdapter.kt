package com.javi_macbook.restaurapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.javi_macbook.restaurapp.R
import com.javi_macbook.restaurapp.model.Dish

class DishRecyclerViewAdapter(val dish: List<Dish>) : RecyclerView.Adapter<DishRecyclerViewAdapter.DishViewHolder>(){

    // Me creo un atributo onClickListener para ir a la actividad de Detalle
    var onClickListener: View.OnClickListener? = null

    // Se crea el ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishViewHolder {
        // Creo la vista y inflo el Layout Content_dish
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.content_dish, parent, false)
        // le decimos que nos informe cuando se pulse un ViewHolder
        view.setOnClickListener(onClickListener)
        return DishViewHolder(view)
    }

    // Se le pasa el modelo al ViewHolder
    override fun onBindViewHolder(holder: DishViewHolder?, position: Int) {
        holder?.bindDish(dish[position], position)
    }

    override fun getItemCount(): Int {
        return dish.size
    }

    inner class DishViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        // Accedemos a las vistas
        val dishImage = itemView.findViewById<ImageView>(R.id.dish_image)
        val dishName = itemView.findViewById<TextView>(R.id.dish_name)
        val dishDescription = itemView.findViewById<TextView>(R.id.dish_description)
        val dishPrice = itemView.findViewById<TextView>(R.id.dish_price)

        fun bindDish(dish: Dish, position: Int) {
            // Necesitamos el contexto de una vista para hacer getString
            val context = itemView.context

            // Actualizo la vista(itemView) con el modelo (Dish)
            dishImage.setImageResource(dish.image)
            dishName.text = dish.name
            dishDescription.text = dish.description

            val priceString = context.getString(R.string.dish_price, dish.price)
            dishPrice.text = priceString

        }
    }
}