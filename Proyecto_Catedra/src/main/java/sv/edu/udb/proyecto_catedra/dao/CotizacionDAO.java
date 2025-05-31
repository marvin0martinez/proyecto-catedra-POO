package sv.edu.udb.proyecto_catedra.dao;

import sv.edu.udb.proyecto_catedra.model.Cotizacion;
import sv.edu.udb.proyecto_catedra.model.Estado;

import java.util.List;

public interface CotizacionDAO {

    void crearCotizacion(Cotizacion cotizacion);
    void editarCotizacion(Cotizacion cotizacion);
    void cambiarEstadoCotizacion(int idCotizacion, Estado estado);
    Cotizacion obtenerCotizacion(int idCotizacion);
    List<Cotizacion> listarCotizaciones();
    List<Cotizacion> listarCotizacionesPorCliente(int idCliente);
    float calcularCostoTotal(int idCotizacion);
}
