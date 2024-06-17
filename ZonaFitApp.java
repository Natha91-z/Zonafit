package Presentacion;

import datos.ClienteDao;
import datos.IClienteDAO;
import zona_fit_dominio.Cliente;

import java.util.Scanner;

public class ZonaFitApp {
    public static void main(String[] args) {
        zonaFitApp();
    }

    private static void zonaFitApp(){
        var salir = false;
        var consola = new Scanner(System.in);
        // Creamos un objeto de la clase cliente Dao
        var clienteDao = new ClienteDao() {
            @Override
            public Object agregarClientePor(Cliente nuevoCliente) {
                return null;
            }
        };
        while (!salir){
            try {
                var opcion = mostrarMenu(consola);
                salir = ejecutarOpciones(consola, opcion, clienteDao);
            }catch (Exception e){
                System.out.println("Error al ejecutar opciones " + e.getMessage());
            }
            System.out.println();
        }
    }
    private static int mostrarMenu(Scanner consola){
        System.out.print("""
                *** Zona Fit (GYM)
                1. Listar Clientes
                2. Buscar Clientes
                3. Agrar Cliente
                4. Modificar Cliente
                5. Eliminar Cliente
                6, Salir
                Elije una opcion:\s""");
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarOpciones(Scanner consola, int opcion, IClienteDAO clienteDAO){
        var salir = false;
        switch (opcion){
            case 1 -> { // 1, Listar Clientes
                System.out.println("Listado de Clientes");
                var clientes = clienteDAO.listarclientes();
                clientes.forEach(System.out::println);
            }
            case 2 -> { // Buscar cliente por id
                System.out.println("Introduce el id del cliente");
                var idCliente = Integer.parseInt(consola.nextLine());
                var cliente = new  Cliente(idCliente);
                var encontrado = clienteDAO.buscarClientePorId(cliente);
                if (encontrado)
                    System.out.println("Cliente encontrado: " + cliente);
                else
                    System.out.println("Cliente No encontrado: "+ cliente);
            }
            case 3 -> { // 3. Agregar cliente
                System.out.println("- Agregar Cliente-");
                System.out.print("Nombre: ");
                var nombre = consola.nextLine();
                System.out.print("Apellido: ");
                var apellido = consola.nextLine();
                System.out.print("Membresia: ");
                var membresia = Integer.parseInt(consola.nextLine());

                // Objeto cliente sin el id
         //       var cliente = new  Cliente(nombre, apellido, membresia);
         //       var agregado = clienteDAO.agregarClientePor(cliente);
         //       if (agregado) {
         //           System.out.println(" Cliente agregado: " + cliente);
         //       } else
         //           System.out.println("Cliente No agregado" + cliente);

            }
            default -> throw new IllegalStateException("Unexpected value: " + opcion);
        }
        return salir;
    }
}
