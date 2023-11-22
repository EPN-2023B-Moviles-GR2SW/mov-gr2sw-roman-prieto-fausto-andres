import java.util.*
import kotlin.collections.ArrayList

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

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(arregloEstatico)

    //Arreglo Dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1, 2, 3, 4, 5, 6, 7 ,8 ,9 ,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //FOR EACH -> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico.forEach{ valorActual:Int ->
        println("Valor actual: ${valorActual}")
    }
    //it (en ingles eso) significa el elemento iterado
    arregloDinamico.forEach{ println("Valor actual: ${it}")}
    arregloEstatico
        .forEachIndexed{ indice:Int, valorActual: Int ->
            println("Valor ${valorActual} Indice: ${indice}")
        }
    println(respuestaForEach)

    //MAP -> Muta el arreglo (Cambia el arreglo)
    //1) Enviemos el nuevo valor de la iteración
    //2) Nos devuelve es un Nuevo ARREGLO
    //con los valores modificados
    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map {it + 15}

    //Filter -> Filtrar el arreglo
    //1) Devolver una expresi[on (TRUE o FALSE)
    //2) Nuevo arreglo filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            //Expresion Condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter{
        it <=5
    }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //OR AND
    //OR -> ANY (Alguno cumple?)
    //AND -> ALL (Todos cumple?)

    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5)
        }
    println(respuestaAny) //true

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }
    println(respuestaAll)//false

    //REDUCE -> Valor acumulado
    //Valor acumulado = 0 (Siempre 0 en lenguaje Kotlin)
    // [1,2,3,4,5] -> Sume todos los valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 + 1 = 1 ->Iteracion 1
    // valorIteracion2 = valorIteracion1 + 2 = 1 + 2 = 3 ->Iteracion 2
    // valorIteracion3 = valorIteracion2 + 3 = 3 + 3 = 6 ->Iteracion 3
    // valorIteracion4 = valorIteracion3 + 4 = 6 + 4 = 10 ->Iteracion 4
    // valorIteracion5 = valorIteracion4 + 5 = 10 + 5 = 15 ->Iteracion 5

    val respuestaReduce: Int = arregloDinamico
        .reduce {
            acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual) // Logica negocio
        }
    println(respuestaReduce) //78



}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ) {//Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( //Constructor PRIMARIO
    //Ejemplo
    //uno: Int, (Parametro (sin modificar de acceso))
    //private var uno: Int, //Propiedad Publica Clase numeros.uno
    //var.uno: Int, //Propiedad de la clase (por defecto es Public)
    //public var uno: Int,
    protected val numeroUno: Int, //Propiedad de la clase protected  numeros.numeroUno
    protected val numeroDos: Int, //Propiedad de la clase protected  numeros.numeroDos
){
    // var cedula:string = "" (public es por defecto)
    // private valorCalculado: Int = 0 (private)
    init {
        this.numeroUno; this.numeroDos; //this es opcional
        numeroUno; numeroDos;
        println("Inicializacion");
    }
}

class Suma(
    unoParametro: Int, //Parametro
    dosParametro: Int, //Parametro

): Numeros(unoParametro, dosParametro){ //Extendiendo y mandando los parametros (super)
    init { //Bloque codigo constructor primario
        numeroDos; numeroUno;
    }
    constructor(
        uno: Int?,
        dos: Int
    ):this(
        if(uno == null) 0 else uno,
        dos
    )
    constructor(
        uno: Int,
        dos: Int?
    ): this(
        uno,
        if(dos == null) 0 else dos,
    )
    constructor(
        uno: Int?,
        dos: Int?
    ): this(
        if(uno == null) 0 else uno,
        if(dos == null) 0 else dos,

    )
    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial
        agregarHistorial(total)
        return total
    }
    companion object { //Atributos y metodos Compartidos
        //entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }

    }
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