package com.javi_macbook.restaurapp.fragment

import android.app.AlertDialog
import android.app.Fragment
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewSwitcher
import com.javi_macbook.restaurapp.CONSTANT_URL_JSON
import com.javi_macbook.restaurapp.R
import com.javi_macbook.restaurapp.activity.DetailActivity
import com.javi_macbook.restaurapp.adapter.DishRecyclerViewAdapter
import com.javi_macbook.restaurapp.model.Dish
import com.javi_macbook.restaurapp.model.Table
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class DishFragment : Fragment() {

    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        FORECAST(1)
    }

    companion object {

        val ARG_TABLE = "ARG_TABLE"

        // Creo esta funcion para recibir la instancia de este Fragment, con la mesa como argumento
        fun newInstance(table: Table): DishFragment {
            val fragment = DishFragment()
            val arguments = Bundle()
            arguments.putSerializable(ARG_TABLE, table)
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var root: View
    lateinit var viewSwitcher: ViewSwitcher
    lateinit var dishList: RecyclerView

    var table: Table? = null
        set(value){
            field = value
            if(value != null){
                dish = value.dish
            }
        }

    var dish: List<Dish>? = null
        set(value){
            field = value

            // Actualizo la vista con el modelo
            if (value != null) {

                // Asignamos el adapter al RecyclerView ahora que tenemos datos
                val adapter = DishRecyclerViewAdapter(value)
                dishList.adapter = adapter

                // Le digo al RecyclerViewAdapter que me informe cuando pulsen sus vistas
                adapter.onClickListener = View.OnClickListener { v:View? ->
                    // Aqui me entero de que se ha pulsado y la posicion
                    val position = dishList.getChildAdapterPosition(v)
                    val dishToShow = value[position]
                    // Lanzamos la actividad
                    startActivity(DetailActivity.intent(activity, dishToShow))

                }

                viewSwitcher.displayedChild = VIEW_INDEX.FORECAST.index
                // SuperCache
                table?.dish = value
            }
            else {
                updateDish()
            }
        }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        // Si inflater es distinto de null
        if (inflater != null) {

            root = inflater.inflate(R.layout.fragment_dish, container, false)
            viewSwitcher = root.findViewById(R.id.view_switcher)
            viewSwitcher.setInAnimation(activity, android.R.anim.fade_in)
            viewSwitcher.setOutAnimation(activity, android.R.anim.fade_out)

            // Accedemos al RecyclerView
            dishList = root.findViewById(R.id.dish_list)

            // Le decimos como debe visualizarse (LayoutManager)
            dishList.layoutManager = LinearLayoutManager(activity)

            // Le decimos como animarse
            dishList.itemAnimator = DefaultItemAnimator()

            // El RecyclerView necesita un adapter,
            // aunque aquí aún no podemos ponerlo, ya que no tenemos los datos


            if (arguments != null){
                table = arguments.getSerializable(ARG_TABLE) as? Table
            }
        }

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    private fun updateDish() {
        viewSwitcher.displayedChild = VIEW_INDEX.LOADING.index

        async(UI) {
            // Esto ejecuta la descarga en 2º plano
            val newDish: Deferred<List<Dish>?> = bg {
                downloadDish()
            }

            val downloadedDish = newDish.await()

            if (downloadedDish != null) {
                // Tó ha ido bien, se lo asigno al atributo dish
                dish = downloadedDish
            }
            else {
                // Ha habido algún tipo de error, se lo decimos al usuario con un diálogo
                AlertDialog.Builder(activity)
                        .setTitle("Error")
                        .setMessage("No me pude descargar la información de los platos")
                        .setPositiveButton("Reintentar", { dialog, _ ->
                            dialog.dismiss()
                            updateDish()
                        })
                        .setNegativeButton("Salir", { _, _ -> activity.finish() })
                        .show()
            }

        }

    }

    fun downloadDish(): List<Dish>? {
        try {
            // Simulamos un retardo
            Thread.sleep(1000)

            // Descargo la informacion de internet
            val url = URL(CONSTANT_URL_JSON)
            val jsonString = Scanner(url.openStream(), "UTF-8").useDelimiter("\\A").next()

            // Analizamos los datos que nos acabamos de descargar
            val jsonRoot = JSONObject(jsonString)
            val listDish = jsonRoot.getJSONArray("platos")

            // Nos creamos una lista mutable donde añadir los diferentes platos del JSON
            val dishes = mutableListOf<Dish>()

            // Recorremos la lista del JSON
            for (dishIndex in 0..listDish.length() - 1){
                val plato = listDish.getJSONObject(dishIndex)
                val name = plato.getString("nombre")
                val description = plato.getString("descripcion")
                val alergens = plato.getJSONArray("alergenos")
                val price = plato.getDouble("precio").toFloat()
                val image = plato.getString("image")

                // Convertimos el texto imageString a Drawable
                val imageInt = image.toInt()
                val imageResource = when (imageInt){
                    1 -> R.drawable.porra
                    2 -> R.drawable.ajo_blanco
                    3 -> R.drawable.porrilla_setas
                    4 -> R.drawable.gazpachuelo
                    5 -> R.drawable.huevos_rotos
                    6 -> R.drawable.pavo_salsa
                    7 -> R.drawable.callos
                    8 -> R.drawable.bienmesabe
                    9 -> R.drawable.torrijas
                    10 -> R.drawable.pestinos
                    else -> R.drawable.noimage
                }

                // Transformo el JSONArray en una lista de Strings
                val listAlergens = mutableListOf(String())
                if (alergens != null){
                    for (i in 0 until alergens.length()){
                        listAlergens.add(alergens.getString(i))
                    }
                }


                dishes.add(Dish(name, imageResource, price, description, listAlergens))
            }

            return dishes

        } catch (ex: Exception){
            ex.printStackTrace()
        }

        return null
    }

}