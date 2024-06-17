package datos;

import zona_fit.conexion.Conexion;
import zona_fit_dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static zona_fit.conexion.Conexion.getConexion;

public abstract class ClienteDao implements IClienteDAO{
    @Override
    public List<Cliente> listarclientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        var sql = "SELECT * FROM cliente ORDER BY id";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                var cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        }catch (Exception e){
            System.out.println("Error al listar clientes: "+ e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion: " + e.getMessage());
            }

        }

        return clientes;
    }


    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        var con = getConexion();
        var sql = "SELET * FROM cliente WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            rs = ps.executeQuery();
            if (rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.getApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        }catch (Exception e){
            System.out.println("Error al recuperar cliente por id: "+ e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion: "+ e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql =" INSERTE INTO cliente(nombre, apellido, membresia)"
                + "VALUES(?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.execute();
            return true;

        }catch (Exception e){
            System.out.println("Error al agregar cliente: " +e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion" + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        var sql = "UODATE cliente SET nombre=>, apellido=? " +"WHERE id = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getMembresia());
            ps.setInt(4, cliente.getId());
            ps.execute();
        }catch (Exception e){
            System.out.println("Error al modificar cliente");
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion");
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM cliente WHERE id =?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, cliente.getId());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println(" Error al eliminar cliente: "+ e.getMessage());
        }
        finally {
            try {
                con.close();
            }catch (Exception e){
                System.out.println("Error al cerrar conexion: "+ e.getMessage());
            }
        }

        return false;
    }

    public static void main(String[] args) {
        IClienteDAO clienteDAO = new ClienteDao() {
            @Override
            public Object agregarClientePor(Cliente nuevoCliente) {
                return null;
            }
        };

        //Listar clientes
//        System.out.println("*** Listar Clientes ***");
//        var clientes = clienteDAO.listarclientes();
//        clientes.forEach(System.out::println);

        //Buscar pot id
        //       var cliente1 = new Cliente(2);
        //       System.out.println("Clientes antes de la busqueda: "+ cliente1);
        //       var encontrado = clienteDAO.buscarClientePorId(cliente1);
        //       if (encontrado)
        //           System.out.println("Cliente encontrado: "+ cliente1);
        //       else
        //           System.out.println("No se encontro cliente: "+ cliente1.getId());

        //Modificar cliente
 //       var modoficarcliente = new Cliente(1, "Carlos Daniel", "Flores", 100);
 //       var modificado = clienteDAO.modificarCliente(modoficarcliente);
 //       if (modificado)
 //           System.out.println("Cliente modificado: " + modoficarcliente);
 //       else
 //           System.out.println("No se modifico cliente: " + modoficarcliente);

    }
}
