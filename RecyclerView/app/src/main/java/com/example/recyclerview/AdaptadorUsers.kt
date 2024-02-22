package com.example.recyclerview

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AdaptadorUsers(private var lista: ArrayList<User>, private var contexto: Context): RecyclerView.Adapter<AdaptadorUsers.ViewHolder>() {

    class ViewHolder(var vista: View, var contexto:Context) : RecyclerView.ViewHolder(vista){
        fun bind(user: User){
            val imageView = vista.findViewById<ImageView>(R.id.elplvUserProfile) // Asegúrate de que el ID corresponda.
            imageView.setImageResource(user.idImagen)
            val imageView2 = vista.findViewById<ImageView>(R.id.elplvUser) // Asegúrate de que el ID corresponda.
            imageView2.setImageResource(user.idImagen)
            val text = vista.findViewById<TextView>(R.id.elpTvNombre)
            text.setText(user.nombre)
            val text2 = vista.findViewById<TextView>(R.id.elpTvTag)
            text2.setText(user.tag)
            imageView2.setOnClickListener{
                contexto.startActivity(Intent(contexto, VisorImagen::class.java).putExtra("user",user))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.elemento_lista_user, parent, false), contexto)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }
}