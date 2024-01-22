package com.example.examenb1

import Empresa

class BaseDatosMemoria {
    companion object{
        val empresas = ArrayList<Empresa>();

        //------ Metodos de CRUD Libro --------

        fun agregarEmpresa(empresa:Empresa){
            empresas.add(empresa);
        }

        fun actualizarempresa(id:Int, empresaNueva:Empresa){
            val empresa = empresas.getOrNull(id);
            if(empresa != null){
                println("Empresa actual antes de la actualización: $empresa")
                empresas[id] = empresaNueva
                println("Empresa actualizada: $empresaNueva")
            }else{
                println("Empresa no encontrado con el indice ${id}")
            }

        }

        fun eliminarEmpresa(id:Int):Boolean{
            val empresa = empresas.getOrNull(id);
            if(empresa != null){
                empresas.remove(empresa);
                println("Empresa eliminada: $empresa");
                return true;
            }else{
                println("Empresa no encontrada con el indice ${id}");
                return false;
            }
        }

        fun buscarEmpresaPorId(idEmpresa: Int):Empresa?{
            val empresa = empresas.getOrNull(idEmpresa);
            if(empresa != null){
                return empresa
            }else{
                println("Empresa no encontrada con el indice ${idEmpresa}");
                return null;
            }
        }

        fun obtenerProductos(empresa:Empresa):MutableList<Producto>{
            var listProductos: MutableList<Producto> = mutableListOf();
            for(idx in empresa.productos.indices){
                println("${idx}: ${empresa.productos.get(idx)}");
                listProductos.add(empresa.productos.get(idx))
            }
            return listProductos;
        }

        fun agregarProducto(idEmpresa:Int, producto:Producto){
            val empresa = empresas.getOrNull(idEmpresa);
            if(empresa != null){
                empresa.productos.add(producto);
                println("Producto agregada a la empresa '${empresa.nombre}'.")
            }else{
                println("Empresa no encontrada con el  ${idEmpresa}");
            }
        }

        fun actualizarProducto(idEmpresa: Int,idProducto: Int,recetaNew: Producto){
            val empresa = empresas.getOrNull(idEmpresa);
            if(empresa != null){
                val producto = empresa.productos.getOrNull(idProducto);
                if(producto != null){
                    empresa.productos[idProducto] = recetaNew;
                }else{
                    println("Producto no encontrado con el id ${idProducto}'.")
                }
            }else{
                println("Empresa no encontrada con el id  ${idProducto}");
            }
        }

        fun eliminarProducto(idEmpresa:Int, idProducto:Int ):Boolean{
            val empresa = empresas.getOrNull(idEmpresa);
            if(empresa != null){
                val producto = empresa.productos.getOrNull(idProducto);
                if(producto != null){
                    empresa.productos.remove(producto);
                    println("Producto '${producto.nombre}' eliminado de la empresa '${empresa.nombre}'.")
                    return true;
                }else{
                    println("Producto no encontrado con el id ${idProducto}'.")
                    return false;
                }
            }else{
                println("Empresa no encontrada con el id  ${idEmpresa}");
                return false;
            }
        }
    }
}