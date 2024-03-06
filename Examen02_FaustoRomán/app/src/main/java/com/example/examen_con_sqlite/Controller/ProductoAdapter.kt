package com.example.examen_con_sqlite.Controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.examen_con_sqlite.Model.Producto

class ProductoAdapter(context: Context, productos: List<Producto>) :
    ArrayAdapter<Producto>(context, 0, productos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
        }

        // Obtener la canción actual
        val currentProducto = getItem(position)

        // Configura el texto del elemento de la lista con el nombre de la canción
        val textView = listItemView?.findViewById<TextView>(android.R.id.text1)
        textView?.text = currentProducto?.nombre

        return listItemView!!
    }
}