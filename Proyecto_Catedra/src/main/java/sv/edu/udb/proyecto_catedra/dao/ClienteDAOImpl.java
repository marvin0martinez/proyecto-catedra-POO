package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.connection.Conexion;
import sv.edu.udb.proyecto_catedra.model.Cliente;
import sv.edu.udb.proyecto_catedra.model.Estado;
import sv.edu.udb.proyecto_catedra.model.TipoPersona;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void crearCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, documentoIdentificacion, tipoPersona, telefono, " +
                "correo, direccion, estado, creadoPor, fechaCreacion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDocumentoIdentificacion());
            stmt.setString(3, cliente.getTipoPersona().name());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getCorreo());
            stmt.setString(6, cliente.getDireccion());
            stmt.setString(7, cliente.getEstado().name());
            stmt.setString(8, cliente.getCreadoPor());
            stmt.setTimestamp(9, new Timestamp(cliente.getFechaCreacion().getTime()));

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación del cliente falló, no se insertaron filas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cliente.setIdCliente(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La creación del cliente falló, no se obtuvo el ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear el cliente", e);
        }
    }

    @Override
    public void editarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, documentoIdentificacion = ?, tipoPersona = ?, " +
                "telefono = ?, correo = ?, direccion = ?, estado = ?, " +
                "fechaActualizacion = ? WHERE idCliente = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getDocumentoIdentificacion());
            stmt.setString(3, cliente.getTipoPersona().name());
            stmt.setString(4, cliente.getTelefono());
            stmt.setString(5, cliente.getCorreo());
            stmt.setString(6, cliente.getDireccion());
            stmt.setString(7, cliente.getEstado().name());
            stmt.setTimestamp(8, new Timestamp(new Date().getTime()));
            stmt.setInt(9, cliente.getIdCliente());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La actualización del cliente falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al actualizar el cliente", e);
        }
    }

    @Override
    public void cambiarEstadoCliente(int idCliente, Estado estado) {
        String sql = "UPDATE clientes SET estado = ?, " +
                (estado == Estado.INACTIVO ?
                        "fechaInactivacion = ? " :
                        "fechaActualizacion = ? ") +
                "WHERE idCliente = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado.name());
            stmt.setTimestamp(2, new Timestamp(new Date().getTime()));
            stmt.setInt(3, idCliente);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("El cambio de estado del cliente falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cambiar el estado del cliente", e);
        }
    }

    @Override
    public Cliente obtenerCliente(int idCliente) {
        String sql = "SELECT * FROM clientes WHERE idCliente = ?";
        Cliente cliente = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = mapearCliente(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el cliente", e);
        }

        return cliente;
    }

    @Override
    public List<Cliente> listarClientes() {
        String sql = "SELECT * FROM clientes ORDER BY nombre";
        List<Cliente> clientes = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al listar los clientes", e);
        }

        return clientes;
    }

    // Método auxiliar para mapear un ResultSet a un objeto Cliente
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("idCliente"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setDocumentoIdentificacion(rs.getString("documentoIdentificacion"));
        cliente.setTipoPersona(TipoPersona.valueOf(rs.getString("tipoPersona")));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setCorreo(rs.getString("correo"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setEstado(Estado.valueOf(rs.getString("estado")));
        cliente.setCreadoPor(rs.getString("creadoPor"));
        cliente.setFechaCreacion(rs.getTimestamp("fechaCreacion"));
        cliente.setFechaActualizacion(rs.getTimestamp("fechaActualizacion"));
        cliente.setFechaInactivacion(rs.getTimestamp("fechaInactivacion"));

        return cliente;
    }
}