package com.example.examen_con_sqlite.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class BaseDeDatosHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NOMBRE, null, DATABASE_VERSION) {

    companion object{
        const val DATABASE_NOMBRE = "cartera_empresas"
        const val DATABASE_VERSION = 1
        const val TABLA_EMPRESA = "empresa"
        const val TABLA_PRODUCTO = "producto"

    }

    override fun onCreate(db: SQLiteDatabase?) {

        // Crear Scripts de SQL para una Base de Datos

        // Creacion de la tabla Album

        val scriptSQLCrearTablaEmpresa =
            """
                CREATE TABLE $TABLA_EMPRESA( 
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT,
                    ceo TEXT,
                    anioDeRegistro INTEGER,
                    sector TEXT             
                )  
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEmpresa)

        // Creacion de la tabla Cancion

        val scriptSQLCrearTablaProducto =
            """
                CREATE TABLE ${TABLA_PRODUCTO}( 
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    empresaId INTEGER,
                    nombre TEXT,
                    tipo TEXT,
                    cantidad INTEGER,
                    precio INTEGER,         
                    FOREIGN KEY(empresaId) REFERENCES $TABLA_EMPRESA(id)
                    
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaProducto)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


}