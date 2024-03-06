package com.example.examen_con_sqlite.Controller

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.examen_con_sqlite.Model.Empresa

class EmpresaAdapter(context: Context, val empresas: MutableList<Empresa>) : ArrayAdapter<Empresa>(context, 0, empresas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        }

        // Obtener el álbum actual
        val currentEmpresa = getItem(position)

        // Configura el texto del elemento de la lista con el nombre del álbum
        val textView = listItemView?.findViewById<TextView>(android.R.id.text1)
        textView?.text = currentEmpresa?.nombre

        return listItemView!!
    }

    // Función para actualizar la lista de álbumes
    fun actualizarLista(nuevaLista: List<Empresa>) {
        empresas.clear()
        empresas.addAll(nuevaLista)
        notifyDataSetChanged()
    }

}