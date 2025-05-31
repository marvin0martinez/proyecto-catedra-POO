package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.connection.Conexion;
import sv.edu.udb.proyecto_catedra.model.Empleado;
import sv.edu.udb.proyecto_catedra.model.Estado;
import sv.edu.udb.proyecto_catedra.model.TipoContratacion;
import sv.edu.udb.proyecto_catedra.model.TipoPersona;

import java.sql.*;
import  java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public void crearEmpleado(Empleado empleado) {
        String sql = "INSERT INTO empleados (nombre, documentoIdentificacion, tipoPersona, tipoContratacion, "
                + "telefono, correo, direccion, estado, creadoPor, fechaCreacion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getDocumentoIdentificacion());
            stmt.setString(3, empleado.getTipoPersona().name());
            stmt.setString(4, empleado.getTipoContratacion().name());
            stmt.setString(5, empleado.getTelefono());
            stmt.setString(6, empleado.getCorreo());
            stmt.setString(7, empleado.getDireccion());
            stmt.setString(8, empleado.getEstado().name());
            stmt.setString(9, empleado.getCreadoPor());
            stmt.setTimestamp(10, new Timestamp(empleado.getFechaCreacion().getTime()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación del empleado falló, no se insertaron filas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    empleado.setIdEmpleado(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La creación del empleado falló, no se obtuvo el ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear el empleado", e);
        }
    }

    @Override
    public void editarEmpleado(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, documentoIdentificacion = ?, tipoPersona = ?, "
                + "tipoContratacion = ?, telefono = ?, correo = ?, direccion = ?, estado = ?, "
                + "fechaActualizacion = ? WHERE idEmpleado = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, empleado.getNombre());
            stmt.setString(2, empleado.getDocumentoIdentificacion());
            stmt.setString(3, empleado.getTipoPersona().name());
            stmt.setString(4, empleado.getTipoContratacion().name());
            stmt.setString(5, empleado.getTelefono());
            stmt.setString(6, empleado.getCorreo());
            stmt.setString(7, empleado.getDireccion());
            stmt.setString(8, empleado.getEstado().name());
            stmt.setTimestamp(9, new Timestamp(new Date().getTime()));
            stmt.setInt(10, empleado.getIdEmpleado());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La actualización del empleado falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el empleado", e);
        }
    }

    @Override
    public void cambiarEstadoEmpleado(int idEmpleado, Estado estado) {
        String sql = "UPDATE empleados SET estado = ?, " +
                (estado == Estado.INACTIVO ?
                        "fechaInactivacion = ? " :
                        "fechaActualizacion = ? ") +
                "WHERE idEmpleado = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado.name());
            stmt.setTimestamp(2, new Timestamp(new Date().getTime()));
            stmt.setInt(3, idEmpleado);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("El cambio de estado del empleado falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar el estado del empleado", e);
        }
    }

    @Override
    public Empleado obtenerEmpleado(int idEmpleado) {
        String sql = "SELECT * FROM empleados WHERE idEmpleado = ?";
        Empleado empleado = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmpleado);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    empleado = mapearEmpleado(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el empleado", e);
        }

        return empleado;
    }

    @Override
    public List<Empleado> listarEmpleados() {
        String sql = "SELECT * FROM empleados ORDER BY nombre";
        List<Empleado> empleados = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                empleados.add(mapearEmpleado(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los empleados", e);
        }

        return empleados;
    }

    @Override
    public List<Empleado> listarEmpleadosActivos() {
        String sql = "SELECT * FROM empleados WHERE estado = 'ACTIVO' ORDER BY nombre";
        List<Empleado> empleados = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                empleados.add(mapearEmpleado(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar los empleados activos", e);
        }

        return empleados;
    }

    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(rs.getInt("idEmpleado"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setDocumentoIdentificacion(rs.getString("documentoIdentificacion"));
        empleado.setTipoPersona(TipoPersona.valueOf(rs.getString("tipoPersona")));
        empleado.setTipoContratacion(TipoContratacion.valueOf(rs.getString("tipoContratacion")));
        empleado.setTelefono(rs.getString("telefono"));
        empleado.setCorreo(rs.getString("correo"));
        empleado.setDireccion(rs.getString("direccion"));
        empleado.setEstado(Estado.valueOf(rs.getString("estado")));
        empleado.setCreadoPor(rs.getString("creadoPor"));
        empleado.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
        empleado.setFechaActualizacion(rs.getTimestamp("fechaActualizacion"));
        empleado.setFechaInactivacion(rs.getTimestamp("fechaInactivacion"));

        return empleado;
    }
}
