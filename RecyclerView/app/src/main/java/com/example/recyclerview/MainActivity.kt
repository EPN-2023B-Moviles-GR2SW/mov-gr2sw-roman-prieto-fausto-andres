package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inicializa el binding
        setContentView(binding.root) // Usa el root del binding como vista de la actividad
        binding.amRvUsers.layoutManager = LinearLayoutManager(this)
        binding.amRvUsers.adapter = AdaptadorUsers(generarDatosPrueba(), this)
    }

    private fun generarDatosPrueba() : ArrayList<User>{
        val lista = ArrayList<User>()
        lista.add(User(R.drawable.imagen1, "Laura", "Femenino", "@Lau", 21))
        lista.add(User(R.drawable.imagen2, "Maria", "Femenino", "@Mari", 23))
        lista.add(User(R.drawable.imagen1, "Laura", "Femenino", "@Lau", 21))
        lista.add(User(R.drawable.imagen2, "Maria", "Femenino", "@Mari", 23))
        lista.add(User(R.drawable.imagen1, "Laura", "Femenino", "@Lau", 21))
        lista.add(User(R.drawable.imagen2, "Maria", "Femenino", "@Mari", 23))
        return lista
    }
}