import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File


class EmpresaManager {
    private val empresas = ArrayList<Empresa>();
    private val objectMapper =  ObjectMapper();
    private val archivo:String = "data.json";


    fun agregarEmpresa(empresa:Empresa){
        empresas.add(empresa);

    }

    fun obtenerEmpresas(){
        for (idx in empresas.indices){
            println("${idx} - ${empresas.get(idx)}");
        }
    }

    fun mostrarProductos(empresa:Empresa){
        for(idx in empresa.productos.indices){
            println("${idx}: ${empresa.productos.get(idx)}");

        }
    }

    fun obtenerEmpresaPorNombre(nombre:String):Empresa?{
        return empresas.find { it.nombre.equals(nombre,ignoreCase = true) }
    }

    fun actualizarempresa(id:Int, empresaNuevo:Empresa){
        val empresa = empresas.getOrNull(id);
        if(empresa != null){
            println("Empresa actual antes de la actualización: $empresa")
            empresas[id] = empresaNuevo
            println("Empresa actualizada: $empresaNuevo")
        }else{
           println("Empresa no encontrada con el indice ${id}")
        }

    }

    fun buscarEmpresaPorId(idEmpresa: Int):Empresa?{
        val empresa = empresas.getOrNull(idEmpresa);
        if(empresa != null){
            return empresa
        }else{
            println("Libro no encontrado con el indice ${idEmpresa}");
            return null;
        }
    }

    fun eliminarEmpresa(id:Int){
        val empresa = empresas.getOrNull(id);
        if(empresa != null){
            empresas.remove(empresa);
            println("Empresa eliminada: $empresa");
        }else{
            println("Empresa no encontrada con el indice ${id}");
        }
    }

    fun agregarProducto(idEmpresa:Int, producto:Producto){
        val empresa = empresas.getOrNull(idEmpresa);
        if(empresa != null){
            empresa.productos.add(producto);
            println("Producto agregado a la empresa '${empresa.nombre}'.")
        }else{
            println("Empresa no encontrada con el  ${idEmpresa}");
        }
    }
    fun eliminarProducto(idEmpresa:Int, idProducto:Int ){
        val empresa = empresas.getOrNull(idEmpresa);
        if(empresa != null){
            val producto = empresa.productos.getOrNull(idProducto);
            if(producto != null){
                empresa.productos.remove(producto);
                println("Producto '${producto.nombre}' eliminado de la empresa '${empresa.nombre}'.")
            }else{
                println("Producto no encontrado con el id ${idProducto}'.")
            }
        }else{
            println("Empresa no encontrado con el id  ${idEmpresa}");
        }
    }

    // Funciones para guardar y leer un archivo

    fun tieneContenidoJson(): Boolean {
        val data = File(archivo)
        return data.exists() && data.length() > 0
    }

    fun guardarJsonEnArchivov2(listEmpresa: ArrayList<Empresa>){
        val json = objectMapper.writeValueAsString(listEmpresa);
        File(archivo).writeText(json);
    }

    fun leerJsonDesdeArchivoV2(): List<Empresa>{
        if(!tieneContenidoJson()){return listOf()}
        val data = File(archivo).readText();
        val objectData:List<Empresa> = objectMapper.readValue(data, object : TypeReference<List<Empresa>>() {});
        return objectData;
    }

    // Getter
    fun getEmpresas(): ArrayList<Empresa> {
        return empresas
    }

    // Setter
    fun setEmpresas(listaNueva: List<Empresa>) {
        empresas.clear();
        empresas.addAll(listaNueva);
    }

}