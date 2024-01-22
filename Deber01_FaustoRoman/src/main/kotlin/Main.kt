import java.util.Scanner

fun main(args: Array<String>) {
    val sc = Scanner(System.`in`);
    val manager = EmpresaManager();
    val listempresas = manager.leerJsonDesdeArchivoV2();
    if(!listempresas.isEmpty()){
        manager.setEmpresas(listempresas);
    }
    try {
        do {
            println("**********PRODUCTOS DE EMPRESAS**********");
            println("Escoja la opcion  que deseada");
            println("1- Agregar una empresa");
            println("2- Agregar un producto");
            println("3- Mostrar las empresas registradas");
            println("4- Mostrar las empresas por su nombre");
            println("5- Eliminar una empresa");
            println("6- Actualizar la información de una empresa");
            println("7- Eliminar un producto");
            println("0- Salir \n");
            print("Ingresa la opcion que desea: ");
            val opcion = sc.nextInt();
            when(opcion){
                1 -> {
                    print("Ingrese el nombre de la empresa: ")
                    val nombre = sc.next();

                    print("Ingrese el ceo de la empresa: ")
                    val ceo = sc.next();

                    print("Ingrese el año de registro de la empresa: ")
                    val anioDeRegistro = sc.nextInt();

                    print("Ingrese el sector de la empresa: ")
                    val sector = sc.next();

                    val empresa = Empresa(nombre,ceo,anioDeRegistro,sector);
                    manager.agregarEmpresa(empresa)
                    println(" ");
                }
                2->{
                    manager.obtenerEmpresas();
                    print("Ingrese el id de la empresa: ");
                    val idEmpresa = sc.nextInt();

                    print("Ingrese el nombre del producto: ");
                    val nombre: String = sc.next();

                    print("Ingrese el tipo de producto: ");
                    val tipo: String = sc.next();

                    print("Ingrese la cantidad del producto: ");
                    val cantidad:Int = sc.nextInt();

                    print("Ingrese el precio del producto: ");
                    val precio:Int = sc.nextInt();
                    sc.nextLine()

                    println("agregando........");

                    val producto = Producto(nombre,tipo,cantidad,precio);
                    manager.agregarProducto(idEmpresa,producto);
                    println(" ");
                }
                3->{
                    manager.obtenerEmpresas();
                }
                4->{
                    sc.nextLine();
                    print("Ingrese el nombre de la empresa que desea buscar: ");
                    val nombre = sc.nextLine();

                    val empresa = manager.obtenerEmpresaPorNombre(nombre);
                    println(empresa);
                }
                5->{
                    print("Ingrese el id de la empresa que desea eliminar: ");
                    val idEmpresa = sc.nextInt();
                    manager.eliminarEmpresa(idEmpresa);
                }
                6->{
                    manager.obtenerEmpresas();
                    print("Ingrese el id de la empresa: ");
                    val idEmpresa = sc.nextInt();
                    sc.nextLine();

                    print("Ingrese el nombre nuevo de la empresa: ")
                    val nombre = sc.next();

                    print("Ingrese el nuevo nombre del ceo de la empresa: ")
                    val ceo = sc.next();

                    print("Ingrese el nuevo año de registro de la empresa: ")
                    val anioDeRegistro = sc.nextInt();

                    print("Ingrese el nuevo sector de la empresa: ")
                    val sector = sc.next();

                    val empresaActualizado = Empresa(nombre,ceo,anioDeRegistro,sector);
                    manager.actualizarempresa(idEmpresa,empresaActualizado);
                }
                7->{
                    manager.obtenerEmpresas();
                    print("Ingrese el id de la Empresa: ");
                    val idEmpresa = sc.nextInt();
                    sc.nextLine();
                    val empresa = manager.buscarEmpresaPorId(idEmpresa);
                    if(empresa !==null){
                        manager.mostrarProductos(empresa);
                        print("Ingrese el id del producto: ");
                        val idProducto = sc.nextInt();
                        manager.eliminarProducto(idEmpresa,idProducto);
                    }else{
                        println("Empresa no encontrada con el id: ${idEmpresa}");
                    }
                }
            }
        }while (opcion !==0);
    }catch (e: Exception){
        println("Error al ingresar la opcion: ${e.message}");
    }
    manager.guardarJsonEnArchivov2(manager.getEmpresas())
}