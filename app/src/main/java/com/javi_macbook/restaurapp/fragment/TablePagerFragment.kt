package com.javi_macbook.restaurapp.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v13.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.javi_macbook.restaurapp.R
import com.javi_macbook.restaurapp.model.Tables


class TablePagerFragment: Fragment() {
    companion object {
        val ARG_TABLE_INDEX = "ARG_TABLE_INDEX"

        fun newInstance(tableIndex: Int): TablePagerFragment {
            val arguments = Bundle()
            arguments.putInt(ARG_TABLE_INDEX, tableIndex)
            val fragment = TablePagerFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var root: View
    val pager by lazy { root.findViewById<ViewPager>(R.id.view_pager) }

    var initialTableIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_table_pager, container, false)

            // Operador Elvis!! oh yeah!
            initialTableIndex = arguments?.getInt(ARG_TABLE_INDEX) ?: 0

            val adapter = object: FragmentPagerAdapter(fragmentManager) {
                override fun getItem(position: Int) = DishFragment.newInstance(Tables[position])

                override fun getCount() = Tables.count

                override fun getPageTitle(position: Int) = Tables[position].name
            }

            pager.adapter = adapter

            // Método para cambiar el título al cambiar de ViewPager
            pager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {
                    updateTableInfo(position)
                }

            })

            pager.currentItem = initialTableIndex
            updateTableInfo(initialTableIndex)
        }

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.pager, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item?.itemId) {
        R.id.previous -> {
            pager.currentItem = pager.currentItem - 1
            true
        }
        R.id.next -> {
            pager.currentItem++
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        val menuPrev = menu?.findItem(R.id.previous)
        menuPrev?.setEnabled(pager.currentItem > 0)

        val menuNext = menu?.findItem(R.id.next)
        menuNext?.setEnabled(pager.currentItem < Tables.count-1)

    }

    fun updateTableInfo(position: Int) {
        if (activity is AppCompatActivity) {
            val supportActionBar = (activity as? AppCompatActivity)?.supportActionBar
            supportActionBar?.title = Tables[position].name
        }
    }

    fun moveToTable(position: Int) {
        pager.currentItem = position
    }
}