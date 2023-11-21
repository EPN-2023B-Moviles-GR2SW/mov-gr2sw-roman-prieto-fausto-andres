import java.util.*

fun main(){
    println("Hello World!")

    // Existen dos tipos de variables
    //Variables inmutables (son las que no se reasignan)
    val inmutable: String = "Andres";
    //inmutable = "Fausto";
    //se puede obviar el punto y coma
    //Variales mutables (son las que se pueden reasignar)
    var mutable: String = "Fausto";
    mutable = "Andres";

    //siempre vamos a preferir usar val antes que var es decir preferimos usar constantes antes que variables

    // kotlin tiene duck typing es decir si algo parece un pato, nada como pato, grasna como pato es un pato
    //lo mismo para unas variables, es decir si una variable tiene pinta de string el compilador lo va a tratar como
    // un string
    var ejemploVariable = " Fausto Roman"
    val edadEjeplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo

    //Variables primitivas
    val nombreEstudiante: String = "Fausto Roman"
    val sueldo: Double = 0.3
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true
    // CLases java
    val fechaNacimiento: Date = Date()

    //SWITCH
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

}

//void -> Unit
fun imprimirNombre(nombre: String): Unit{
    //"Nombre : " + nombre
    println("Nombre: ${nombre}") //template string
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null, //Opcion null -> nullable
    ): Double{
        //Int -> Int? (nullable)
        //String -> String? (nullable)
        //Date -> Date? (nullable)
    if (bonoEspecial == null){
        return sueldo * (100/tasa)
    } else {
        return sueldo * (100/tasa) + bonoEspecial
    }
}