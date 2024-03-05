package com.example.examen_con_sqlite.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.examen_con_sqlite.Controller.EmpresaCRUD
import com.example.examen_con_sqlite.Controller.ProductoAdapter
import com.example.examen_con_sqlite.Controller.ProductoCRUD
import com.example.examen_con_sqlite.Model.Empresa
import com.example.examen_con_sqlite.Model.Producto
import com.example.examen_con_sqlite.R
import com.google.android.material.snackbar.Snackbar

class VerProductos : AppCompatActivity() {


    lateinit var empresa: Empresa
    lateinit var listaDeProductos: List<Producto>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_productos)


        // Obtener el ID del álbum desde el Intent
        val empresaId = intent.getIntExtra("EMPRESA_ID", -1)


        // Funcionalidad Botones
        val botonRegresarHomeProductos = findViewById<Button>(R.id.btn_Regresar_Productos)
        botonRegresarHomeProductos.setOnClickListener {
            irActividad(MainActivity::class.java)
        }

        val botonAgregarProductos = findViewById<Button>(R.id.btn_Agregar_Productos)
        botonAgregarProductos.setOnClickListener {
            val empresaId = intent.getIntExtra("EMPRESA_ID", -1)
            irActividadConID(AgregarProducto::class.java, empresaId)
        }


        // Verificar si se dio un ID Válido
        if (empresaId != -1){
            // Obtener el álbum correspondiente desde la base de datos
            empresa = EmpresaCRUD(this).obtenerEmpresaPorId(empresaId) ?: Empresa()

            // Obtener la lista de canciones asociadas al álbum
            listaDeProductos = ProductoCRUD(this).obtenerProductosPorEmpresaId(empresaId)

            // Mostrar el nombre del álbum
            val nombreEmpresaTextView = findViewById<TextView>(R.id.txt_Inserte_Nombre)
            nombreEmpresaTextView.text = empresa.nombre

            // Configurar el Adapter para el ListView con el nuevo adaptador
            val adaptadorProductos = ProductoAdapter(this, listaDeProductos)
            val listViewProductos = findViewById<ListView>(R.id.lv_Listado_Productos)
            listViewProductos.adapter = adaptadorProductos
            
            // Obtener el nombre de la canción agregada de los extras del Intent
            val nombreProductoAgregada = intent.getStringExtra("NOMBRE_PRODUCTO_AGREGADA")

            // Verificar si hay un nombre de canción agregada
            if (!nombreProductoAgregada.isNullOrEmpty()) {
                // Mostrar SnackBar con el mensaje de la canción agregada
                mostrarSnackbar("Producto Agregada: $nombreProductoAgregada")
            }
        }

        // Implementar Menú de Opciones en el ListView de Canciones
        val listViewProductos = findViewById<ListView>(R.id.lv_Listado_Productos)
        registerForContextMenu(listViewProductos)

        listViewProductos.setOnItemClickListener { _, _, position, _ ->
            // obtener la canción directamente del adaptador
            val productoSeleccionada = listaDeProductos[position]

        }

    }


    // Crear Menú de Opciones para Canciones
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        // Completar Opciones del Menú
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_producto, menu)
        // Obtener el índice de la canción seleccionada
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        // Acceder a la canción en la posición seleccionada
        val productoSeleccionada = listaDeProductos[position]
        // Almacenar el ID de la canción seleccionada si es necesario
        val idProductoSeleccionada = productoSeleccionada.id

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position

        // Obtener la canción seleccionada desde el adaptador
        val productoSeleccionada = listaDeProductos[position]
        // Almacenar el ID de la canción seleccionada si es necesario
        val idProductoSeleccionada = productoSeleccionada.id
        // Almacenar el ID del álbum asociado a la canción
        val idEmpresaAsociado = productoSeleccionada.empresaId

        when (item.itemId) {
            R.id.mi_EditarProducto -> {
                irActividadConIDs(EditarProducto::class.java, idEmpresaAsociado, idProductoSeleccionada)
                return true

            }
            R.id.mi_EliminarProducto -> {
                // Eliminar la canción seleccionada
                ProductoCRUD(this).eliminarProductoPorId(idProductoSeleccionada)

                // Actualizar la lista de canciones y el adaptador
                listaDeProductos = ProductoCRUD(this).obtenerProductosPorEmpresaId(empresa.id)
                val adaptadorProductos = ProductoAdapter(this, listaDeProductos)
                val listViewProductos = findViewById<ListView>(R.id.lv_Listado_Productos)
                listViewProductos.adapter = adaptadorProductos

                // Mostrar Snackbar
                mostrarSnackbar("Producto Eliminada")
                return true
            }

            else -> return super.onContextItemSelected(item)
        }

    }


    // Funcion
    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }


    fun irActividadConID(clase: Class<*>, empresaId: Int) {
        val intent = Intent(this, clase)
        intent.putExtra("EMPRESA_ID", empresaId)
        startActivity(intent)
    }

    // Funcion para ir a la actividad con ambos IDs
    fun irActividadConIDs(clase: Class<*>, idEmpresa: Int, idProducto: Int) {
        val intent = Intent(this, clase)
        intent.putExtra("EMPRESA_ID", idEmpresa)
        intent.putExtra("PRODUCTO_ID", idProducto)
        startActivity(intent)
    }

    // SnackBar
    private fun mostrarSnackbar(mensaje: String) {
        val rootView = findViewById<View>(android.R.id.content)
        Snackbar.make(rootView, mensaje, Snackbar.LENGTH_SHORT).show()
    }

}