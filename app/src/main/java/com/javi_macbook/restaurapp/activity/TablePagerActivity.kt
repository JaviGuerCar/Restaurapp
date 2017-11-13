package com.javi_macbook.restaurapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.javi_macbook.restaurapp.R
import com.javi_macbook.restaurapp.fragment.TablePagerFragment

class TablePagerActivity : AppCompatActivity() {

    companion object {
        val EXTRA_TABLE_INDEX = "EXTRA_TABLE_INDEX"

        fun intent(context: Context, tableIndex: Int): Intent{
            val intent = Intent(context, TablePagerActivity::class.java)
            intent.putExtra(EXTRA_TABLE_INDEX, tableIndex)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_pager)

        // Configuramos la Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setLogo(R.mipmap.ic_launcher)
        setSupportActionBar(toolbar)

        // Recibimos el índice de la ciudad que queremos mostrar
        val tableIndex = intent.getIntExtra(EXTRA_TABLE_INDEX, 0)

        if (fragmentManager.findFragmentById(R.id.fragment_table_pager) == null) {
            val fragment = TablePagerFragment.newInstance(tableIndex)
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_table_pager, fragment)
                    .commit()
        }

    }


}
