package sv.edu.udb.proyecto_catedra.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sv.edu.udb.proyecto_catedra.dao.*;
import sv.edu.udb.proyecto_catedra.model.Asignacion;
import sv.edu.udb.proyecto_catedra.model.Cliente;
import sv.edu.udb.proyecto_catedra.model.Cotizacion;
import sv.edu.udb.proyecto_catedra.model.Estado;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "CotizacionController", urlPatterns = {"/cotizaciones"})
public class CotizacionController extends HttpServlet {
    private CotizacionDAO cotizacionDAO;
    private ClienteDAO clienteDAO;
    private AsignacionDAO asignacionDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        cotizacionDAO = new CotizacionDAOImpl();
        clienteDAO = new ClienteDAOImpl();
        asignacionDAO = new AsignacionDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "listar";
        }

        try {
            switch (action) {
                case "agregar":
                    mostrarFormularioAgregar(request, response);
                    break;
                case "editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "eliminar":
                    eliminarCotizacion(request, response);
                    break;
                case "cambiarEstado":
                    cambiarEstadoCotizacion(request, response);
                    break;
                case "ver":
                    verCotizacion(request, response);
                    break;
                case "verAsignacion":
                    verAsignacion(request, response);
                    break;
                case "calcularCosto":
                    calcularCostoTotal(request, response);
                    break;
                default:
                    listarCotizaciones(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("agregar".equals(action)) {
                agregarCotizacion(request, response);
            } else if ("editar".equals(action)) {
                editarCotizacion(request, response);
            } else {
                listarCotizaciones(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    private void listarCotizaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cotizacion> cotizaciones = cotizacionDAO.listarCotizaciones();
        request.setAttribute("cotizaciones", cotizaciones);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cotizacion/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioAgregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Cliente> clientes = clienteDAO.listarClientes();
        request.setAttribute("clientes", clientes);
        request.setAttribute("estados", Estado.values());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cotizacion/agregar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCotizacion = Integer.parseInt(request.getParameter("id"));
        Cotizacion cotizacion = cotizacionDAO.obtenerCotizacion(idCotizacion);

        if (cotizacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cotización no encontrada");
            return;
        }

        List<Cliente> clientes = clienteDAO.listarClientes();
        request.setAttribute("cotizacion", cotizacion);
        request.setAttribute("clientes", clientes);
        request.setAttribute("estados", Estado.values());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cotizacion/editar.jsp");
        dispatcher.forward(request, response);
    }

    private void verCotizacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCotizacion = Integer.parseInt(request.getParameter("id"));
        Cotizacion cotizacion = cotizacionDAO.obtenerCotizacion(idCotizacion);

        if (cotizacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cotización no encontrada");
            return;
        }

        List<Asignacion> asignaciones = asignacionDAO.listarAsignacionesPorCotizacion(idCotizacion);
        Cliente cliente = clienteDAO.obtenerCliente(cotizacion.getIdCliente());

        request.setAttribute("cotizacion", cotizacion);
        request.setAttribute("asignaciones", asignaciones);
        request.setAttribute("cliente", cliente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cotizacion/ver.jsp");
        dispatcher.forward(request, response);
    }

    private void verAsignacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idAsignacion = Integer.parseInt(request.getParameter("id"));
        Asignacion asignacion = asignacionDAO.obtenerAsignacion(idAsignacion);

        if (asignacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Asignación no encontrada");
            return;
        }

        request.setAttribute("asignacion", asignacion);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/cotizacion/verAsignacion.jsp");
        dispatcher.forward(request, response);
    }

    private void agregarCotizacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cotizacion cotizacion = new Cotizacion();
        mapearParametrosCotizacion(request, cotizacion);

        cotizacionDAO.crearCotizacion(cotizacion);

        request.getSession().setAttribute("mensajeExito", "Cotización creada exitosamente");
        response.sendRedirect(request.getContextPath() + "/cotizaciones");
    }

    private void editarCotizacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCotizacion = Integer.parseInt(request.getParameter("idCotizacion"));
        Cotizacion cotizacionExistente = cotizacionDAO.obtenerCotizacion(idCotizacion);

        if (cotizacionExistente == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cotización no encontrada");
            return;
        }

        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(idCotizacion);
        mapearParametrosCotizacion(request, cotizacion);

        cotizacionDAO.editarCotizacion(cotizacion);

        request.getSession().setAttribute("mensajeExito", "Cotización actualizada exitosamente");
        response.sendRedirect(request.getContextPath() + "/cotizaciones");
    }

    private void eliminarCotizacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCotizacion = Integer.parseInt(request.getParameter("id"));
        Cotizacion cotizacion = cotizacionDAO.obtenerCotizacion(idCotizacion);

        if (cotizacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cotización no encontrada");
            return;
        }

        cotizacionDAO.cambiarEstadoCotizacion(idCotizacion, Estado.INACTIVO);

        request.getSession().setAttribute("mensajeExito", "Cotización inactivada exitosamente");
        response.sendRedirect(request.getContextPath() + "/cotizaciones");
    }

    private void cambiarEstadoCotizacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCotizacion = Integer.parseInt(request.getParameter("id"));
        String nuevoEstado = request.getParameter("estado");

        Cotizacion cotizacion = cotizacionDAO.obtenerCotizacion(idCotizacion);
        if (cotizacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cotización no encontrada");
            return;
        }

        Estado estado = Estado.valueOf(nuevoEstado);
        cotizacionDAO.cambiarEstadoCotizacion(idCotizacion, estado);

        String mensaje = estado == Estado.ACTIVO ?
                "Cotización reactivada exitosamente" : "Cotización inactivada exitosamente";

        request.getSession().setAttribute("mensajeExito", mensaje);
        response.sendRedirect(request.getContextPath() + "/cotizaciones");
    }

    private void calcularCostoTotal(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idCotizacion = Integer.parseInt(request.getParameter("id"));
        Cotizacion cotizacion = cotizacionDAO.obtenerCotizacion(idCotizacion);

        if (cotizacion == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Cotización no encontrada");
            return;
        }

        float costoTotal = cotizacionDAO.calcularCostoTotal(idCotizacion);
        cotizacion.setCostoTotal(costoTotal);
        cotizacionDAO.editarCotizacion(cotizacion);

        request.getSession().setAttribute("mensajeExito", "Costo total calculado y actualizado: $" + costoTotal);
        response.sendRedirect(request.getContextPath() + "/cotizaciones?action=ver&id=" + idCotizacion);
    }

    private void mapearParametrosCotizacion(HttpServletRequest request, Cotizacion cotizacion) {
        cotizacion.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
        cotizacion.setEstado(Estado.valueOf(request.getParameter("estado")));
        cotizacion.setFechaInicio(new Date(Long.parseLong(request.getParameter("fechaInicio"))));

        if (request.getParameter("fechaFin") != null && !request.getParameter("fechaFin").isEmpty()) {
            cotizacion.setFechaFin(new Date(Long.parseLong(request.getParameter("fechaFin"))));
        }
    }

    private String obtenerUsuarioSesion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("username");
    }
}