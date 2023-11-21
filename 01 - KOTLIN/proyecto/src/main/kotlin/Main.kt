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

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)

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