package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.model.Cliente;
import sv.edu.udb.proyecto_catedra.model.Estado;

import java.util.List;

public interface ClienteDAO {
    void crearCliente(Cliente cliente);
    void editarCliente(Cliente cliente);
    void cambiarEstadoCliente(int idCliente, Estado estado);


    Cliente obtenerCliente(int idCliente);
    List<Cliente> listarClientes();

}
