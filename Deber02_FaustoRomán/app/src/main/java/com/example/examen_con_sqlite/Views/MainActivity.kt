package com.example.examen_con_sqlite.Views

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import com.example.examen_con_sqlite.Controller.EmpresaAdapter
import com.example.examen_con_sqlite.Controller.EmpresaCRUD
import com.example.examen_con_sqlite.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : ComponentActivity() {

    var posEmpresaSeleccionado = 0
    var idEmpresaSeleccionado = 0
    lateinit var adaptador: EmpresaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Funcionalidad Botones

        val botonAgregarEmpresa = findViewById<Button>(R.id.btn_Agregar_Empresa)
        botonAgregarEmpresa.setOnClickListener{
            irActividad(AgregarEmpresa::class.java)
        }

        // Visualizar ListView

        val listViewEmpresas = findViewById<ListView>(R.id.lv_empresas_almacenadas)

        // Implementar Menú de Opciones en el ListView
        listViewEmpresas.setOnItemClickListener { _, _, position, _ ->
            // Obtener el álbum directamente del adaptador
            val empresaSeleccionado = adaptador.getItem(position)
            openContextMenu(listViewEmpresas)
        }

        // Crear el Adapter
        val listadoDeEmpresas = EmpresaCRUD(this).obtenerTodos()
        adaptador = EmpresaAdapter(this, listadoDeEmpresas.toMutableList())
        listViewEmpresas.adapter = adaptador

        // Verificar si hay un extra en el Intent
        if (intent.hasExtra("NOMBRE_EMPRESA_AGREGADO")) {
            val nombreEmpresaAgregado = intent.getStringExtra("NOMBRE_EMPRESA_AGREGADO")
            // Muestra el Snackbar
            mostrarSnackbar(nombreEmpresaAgregado)
        }

        // Implementar Menú de Opciones en el ListView
        registerForContextMenu(listViewEmpresas)
        listViewEmpresas.setOnItemClickListener { _, _, position, _ ->
            // Obtener el álbum directamente del adaptador
            val empresaSeleccionado = adaptador.getItem(position)

        }

        // Verificar si hay un extra en el Intent
        if (intent.hasExtra("EMPRESA_ACTUALIZADO")) {
            val nombreEmpresaActualizado = intent.getStringExtra("EMPRESA_ACTUALIZADO")
            // Muestra el Snackbar
            mostrarSnackbarEmpresaActualizado(nombreEmpresaActualizado)
        }


    }

    // Crear Menú de Opciones
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        // Completar Opciones del Menú
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_empresa, menu)
        // Obtener el id del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        posEmpresaSeleccionado = position
        // Acceder al objeto Album en la posición seleccionada
        val empresaSeleccionado = adaptador.getItem(position)
        // Obtener el id del Album seleccionado
        idEmpresaSeleccionado = empresaSeleccionado?.id ?: -1
    }

    // Visualizar Menú de Opciones
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        // Obtener el álbum seleccionado desde el adaptador
        val empresaSeleccionado = adaptador.getItem(position)

        // Almacena el ID del álbum seleccionado
        idEmpresaSeleccionado = empresaSeleccionado?.id ?: -1

        when (item.itemId) {
            R.id.mi_VerProductos -> {
                val intent = Intent(this, VerProductos::class.java)
                intent.putExtra("EMPRESA_ID", idEmpresaSeleccionado)
                startActivity(intent)
                return true
            }
            R.id.mi_Editar_Empresa -> {
                val intent = Intent(this, EditarEmpresa::class.java)
                intent.putExtra("EMPRESA_ID", idEmpresaSeleccionado)
                startActivity(intent)
                return true
            }
            R.id.mi_Eliminar_Empresa -> {
                abrirDialogoEliminar()
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }


    // Funciones

    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    // Dialogo

    private fun abrirDialogoEliminar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea Eliminar el Empresa?")
        builder.setPositiveButton("Eliminar") { dialog, which ->
            if (idEmpresaSeleccionado != -1) {
                // Llamada a la función para eliminar el álbum por ID
                EmpresaCRUD(this).borrarEmpresaPorId(idEmpresaSeleccionado)
                // Actualizar la lista de álbumes en el adaptador
                adaptador.actualizarLista(EmpresaCRUD(this).obtenerTodos())
                // Notificar al Adapter que los datos han cambiado
                adaptador.notifyDataSetChanged()
                // Muestra el Snackbar
                mostrarSnackbarEliminado("Empresa Eliminado Exitosamente")
            }
        }
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }


    // SnackBar
    private fun mostrarSnackbar(nombreEmpresa: String?) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, "Empresa agregado: $nombreEmpresa", Snackbar.LENGTH_SHORT).show()
    }

    private fun mostrarSnackbarEliminado(mensaje: String) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }

    fun mostrarSnackbarEmpresaActualizado(nombreEmpresa: String?) {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(findViewById(android.R.id.content), " $nombreEmpresa", Snackbar.LENGTH_SHORT).show()
    }

}

