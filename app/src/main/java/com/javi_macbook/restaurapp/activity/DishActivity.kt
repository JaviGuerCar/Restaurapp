package com.javi_macbook.restaurapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.javi_macbook.restaurapp.R
import com.javi_macbook.restaurapp.fragment.TableListFragment
import com.javi_macbook.restaurapp.fragment.TablePagerFragment
import com.javi_macbook.restaurapp.model.Dish
import com.javi_macbook.restaurapp.model.Table
import com.javi_macbook.restaurapp.model.Tables

class DishActivity : AppCompatActivity(), TableListFragment.OnTableSelectedListener {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish)

        // Comprobamos si en la interfaz tenemos un FrameLayout llamado table_list_fragment
        if (findViewById<View>(R.id.table_list_fragment) != null) {
            // Comprobamos primero que no esta añadido el fragment, porque sino se añade cada vez que recreamos la actividad
            if (fragmentManager.findFragmentById(R.id.table_list_fragment) == null) {
                val fragment = TableListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.table_list_fragment, fragment)
                        .commit()
            }
        }

        // Comprobamos si en la interfaz tenemos un FrameLayout llamado table_pager_fragment
        if (findViewById<View>(R.id.fragment_table_pager) != null) {
            // Comprobamos primero que no esta añadido el fragment, porque sino se añade cada vez que recreamos la actividad
            if (fragmentManager.findFragmentById(R.id.fragment_table_pager) == null) {
                val fragment = TablePagerFragment.newInstance(0)
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_table_pager, fragment)
                        .commit()
            }
        }

    }

    override fun onTableSelected(table: Table?, position: Int) {
        // Obtenemos la referencia al ViewPager
        val tablePagerFragment = fragmentManager.findFragmentById(R.id.fragment_table_pager) as? TablePagerFragment

        // Si no hay viewPager, llamo a la actividad
        if (tablePagerFragment == null) {
            startActivity(TablePagerActivity.intent(this, position))
        } else {
            // Si hay viewPager, movemos el pager su sitio
            tablePagerFragment.moveToTable(position)
        }
    }

}
