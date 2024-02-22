package com.example.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.recyclerview.databinding.ActivityVisorImagenBinding
class VisorImagen : AppCompatActivity() {

    private lateinit var binding: ActivityVisorImagenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisorImagenBinding.inflate(layoutInflater) // Inicializa el binding
        setContentView(binding.root)
        val user = intent.getSerializableExtra("user") as User
        binding.avilvUser.setImageResource(user.idImagen) // Accede a avilvUser a través del binding
    }
}