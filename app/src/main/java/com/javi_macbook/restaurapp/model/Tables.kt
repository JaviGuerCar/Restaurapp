package com.javi_macbook.restaurapp.model

import com.javi_macbook.restaurapp.R
import java.io.Serializable

object Tables : Serializable {
    private var tables: List<Table> = listOf(
            Table("Mesa 1", Dish("Porra Antequerana", R.drawable.porra,15.95f,"Plato antequerano típico","Ninguno")),
            Table("Mesa 2", Dish("Bienmesabe", R.drawable.bienmesabe,15.95f,"Postre antequerano típico","Ninguno"))
//            Table("Mesa 4"),
//            Table("Mesa 5"),
//            Table("Mesa 6"),
//            Table("Mesa 7"),
//            Table("Mesa 8"),
//            Table("Mesa 9"),
//            Table("Mesa 10")
    )

    val count
        get() = tables.size

    operator fun get(index: Int) = tables[index]

    fun toArray() = tables.toTypedArray()

}