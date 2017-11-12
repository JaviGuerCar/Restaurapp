package com.javi_macbook.restaurapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.javi_macbook.restaurapp.R
import com.javi_macbook.restaurapp.fragment.TableListFragment
import com.javi_macbook.restaurapp.model.Dish
import com.javi_macbook.restaurapp.model.Table
import com.javi_macbook.restaurapp.model.Tables

class DishActivity : AppCompatActivity(), TableListFragment.OnTableSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        // Comprobamos primero que no esta añadido el fragment, porque sino se añade cada vez que recreamos la actividad
        if (fragmentManager.findFragmentById(R.id.table_list_fragment) == null){
            val fragment = TableListFragment.newInstance(Tables())
            fragmentManager.beginTransaction()
                    .add(R.id.table_list_fragment, fragment)
                    .commit()
        }

    }

    override fun onTableSelected(table: Table?, position: Int) {
        startActivity(TablePagerActivity.intent(this, position))
    }

}
