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
}