package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.model.Empleado;
import sv.edu.udb.proyecto_catedra.model.Estado;

import java.util.List;

public interface EmpleadoDAO {
    void crearEmpleado(Empleado empleado);
    void editarEmpleado(Empleado empleado);
    void cambiarEstadoEmpleado(int idEmpleado, Estado estado);

    Empleado obtenerEmpleado(int idEmpleado);
    List<Empleado> listarEmpleados();
    List<Empleado> listarEmpleadosActivos();

}
