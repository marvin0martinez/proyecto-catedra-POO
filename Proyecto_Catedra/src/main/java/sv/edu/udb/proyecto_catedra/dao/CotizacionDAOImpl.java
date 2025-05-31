package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.connection.Conexion;
import sv.edu.udb.proyecto_catedra.model.Cotizacion;
import sv.edu.udb.proyecto_catedra.model.Estado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CotizacionDAOImpl implements CotizacionDAO {

    @Override
    public void crearCotizacion(Cotizacion cotizacion) {
        String sql = "INSERT INTO cotizaciones (idCliente, estado, fechaInicio, fechaFin, costoTotal) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cotizacion.getIdCliente());
            stmt.setString(2, cotizacion.getEstado().name());
            stmt.setTimestamp(3, new Timestamp(cotizacion.getFechaInicio().getTime()));

            if (cotizacion.getFechaFin() != null) {
                stmt.setTimestamp(4, new Timestamp(cotizacion.getFechaFin().getTime()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }

            stmt.setFloat(5, cotizacion.getCostoTotal());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La creación de la cotización falló, no se insertaron filas.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cotizacion.setIdCotizacion(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La creación de la cotización falló, no se obtuvo el ID.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear la cotización", e);
        }
    }

    @Override
    public void editarCotizacion(Cotizacion cotizacion) {
        String sql = "UPDATE cotizaciones SET idCliente = ?, estado = ?, fechaInicio = ?, "
                + "fechaFin = ?, costoTotal = ? WHERE idCotizacion = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cotizacion.getIdCliente());
            stmt.setString(2, cotizacion.getEstado().name());
            stmt.setTimestamp(3, new Timestamp(cotizacion.getFechaInicio().getTime()));

            if (cotizacion.getFechaFin() != null) {
                stmt.setTimestamp(4, new Timestamp(cotizacion.getFechaFin().getTime()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }

            stmt.setFloat(5, cotizacion.getCostoTotal());
            stmt.setInt(6, cotizacion.getIdCotizacion());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("La actualización de la cotización falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar la cotización", e);
        }
    }

    @Override
    public void cambiarEstadoCotizacion(int idCotizacion, Estado estado) {
        String sql = "UPDATE cotizaciones SET estado = ? WHERE idCotizacion = ?";

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado.name());
            stmt.setInt(2, idCotizacion);

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("El cambio de estado de la cotización falló, no se afectaron filas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar el estado de la cotización", e);
        }
    }

    @Override
    public Cotizacion obtenerCotizacion(int idCotizacion) {
        String sql = "SELECT * FROM cotizaciones WHERE idCotizacion = ?";
        Cotizacion cotizacion = null;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCotizacion);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cotizacion = mapearCotizacion(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener la cotización", e);
        }

        return cotizacion;
    }

    @Override
    public List<Cotizacion> listarCotizaciones() {
        String sql = "SELECT * FROM cotizaciones ORDER BY fechaInicio DESC";
        List<Cotizacion> cotizaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cotizaciones.add(mapearCotizacion(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las cotizaciones", e);
        }

        return cotizaciones;
    }

    @Override
    public List<Cotizacion> listarCotizacionesPorCliente(int idCliente) {
        String sql = "SELECT * FROM cotizaciones WHERE idCliente = ? ORDER BY fechaInicio DESC";
        List<Cotizacion> cotizaciones = new ArrayList<>();

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    cotizaciones.add(mapearCotizacion(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar las cotizaciones por cliente", e);
        }

        return cotizaciones;
    }

    @Override
    public float calcularCostoTotal(int idCotizacion) {
        String sql = "SELECT SUM(costoBase * (1 + incrementoExtra/100)) AS total " +
                "FROM asignaciones WHERE idCotizacion = ?";
        float total = 0;

        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCotizacion);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getFloat("total");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al calcular el costo total", e);
        }

        return total;
    }

    private Cotizacion mapearCotizacion(ResultSet rs) throws SQLException {
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(rs.getInt("idCotizacion"));
        cotizacion.setIdCliente(rs.getInt("idCliente"));
        cotizacion.setEstado(Estado.valueOf(rs.getString("estado")));
        cotizacion.setFechaInicio(rs.getTimestamp("fechaInicio"));
        cotizacion.setFechaFin(rs.getTimestamp("fechaFin"));
        cotizacion.setCostoTotal(rs.getFloat("costoTotal"));

        return cotizacion;
    }
}