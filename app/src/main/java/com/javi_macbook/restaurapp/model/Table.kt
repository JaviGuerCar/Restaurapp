package com.javi_macbook.restaurapp.model

import java.io.Serializable

data class Table (var name: String, var dish: List<Dish>?) : Serializable {

    constructor(name: String) : this(name, null)

    //Sobreescribo el método toString, para que la lista me devuelva solo el nombre
    override fun toString(): String {
        return name
    }
}