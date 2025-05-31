package sv.edu.udb.proyecto_catedra.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import sv.edu.udb.proyecto_catedra.dao.EmpleadoDAO;
import sv.edu.udb.proyecto_catedra.dao.EmpleadoDAOImpl;
import sv.edu.udb.proyecto_catedra.model.Empleado;
import sv.edu.udb.proyecto_catedra.model.Estado;
import sv.edu.udb.proyecto_catedra.model.TipoContratacion;
import sv.edu.udb.proyecto_catedra.model.TipoPersona;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "EmpleadoController", urlPatterns = {"/empleados"})
public class EmpleadoController extends HttpServlet {
    private EmpleadoDAO empleadoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        empleadoDAO = new EmpleadoDAOImpl();
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
                    eliminarEmpleado(request, response);
                    break;
                case "cambiarEstado":
                    cambiarEstadoEmpleado(request, response);
                    break;
                case "ver":
                    verEmpleado(request, response);
                    break;
                default:
                    listarEmpleados(request, response);
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
                agregarEmpleado(request, response);
            } else if ("editar".equals(action)) {
                editarEmpleado(request, response);
            } else {
                listarEmpleados(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud: " + e.getMessage(), e);
        }
    }

    private void listarEmpleados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Empleado> empleados = empleadoDAO.listarEmpleados();
        request.setAttribute("empleados", empleados);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/empleado/listar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioAgregar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("tipoPersonas", TipoPersona.values());
        request.setAttribute("tipoContrataciones", TipoContratacion.values());
        request.setAttribute("estados", Estado.values());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/empleado/agregar.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        Empleado empleado = empleadoDAO.obtenerEmpleado(idEmpleado);

        if (empleado == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Empleado no encontrado");
            return;
        }

        request.setAttribute("empleado", empleado);
        request.setAttribute("tipoPersonas", TipoPersona.values());
        request.setAttribute("tipoContrataciones", TipoContratacion.values());
        request.setAttribute("estados", Estado.values());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/empleado/editar.jsp");
        dispatcher.forward(request, response);
    }

    private void verEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        Empleado empleado = empleadoDAO.obtenerEmpleado(idEmpleado);

        if (empleado == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Empleado no encontrado");
            return;
        }

        request.setAttribute("empleado", empleado);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/empleado/ver.jsp");
        dispatcher.forward(request, response);
    }

    private void agregarEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Empleado empleado = new Empleado();
        mapearParametrosEmpleado(request, empleado);

        empleado.setCreadoPor(obtenerUsuarioSesion(request));
        empleado.setFechaCreacion(new Date());

        empleadoDAO.crearEmpleado(empleado);

        request.getSession().setAttribute("mensajeExito", "Empleado creado exitosamente");
        response.sendRedirect(request.getContextPath() + "/empleados");
    }

    private void editarEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
        Empleado empleadoExistente = empleadoDAO.obtenerEmpleado(idEmpleado);

        if (empleadoExistente == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Empleado no encontrado");
            return;
        }

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(idEmpleado);
        mapearParametrosEmpleado(request, empleado);

        empleado.setCreadoPor(empleadoExistente.getCreadoPor());
        empleado.setFechaCreacion(empleadoExistente.getFechaCreacion());
        empleado.setFechaActualizacion(new Date());

        empleadoDAO.editarEmpleado(empleado);

        request.getSession().setAttribute("mensajeExito", "Empleado actualizado exitosamente");
        response.sendRedirect(request.getContextPath() + "/empleados");
    }

    private void eliminarEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        Empleado empleado = empleadoDAO.obtenerEmpleado(idEmpleado);

        if (empleado == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Empleado no encontrado");
            return;
        }

        empleadoDAO.cambiarEstadoEmpleado(idEmpleado, Estado.INACTIVO);

        request.getSession().setAttribute("mensajeExito", "Empleado inactivado exitosamente");
        response.sendRedirect(request.getContextPath() + "/empleados");
    }

    private void cambiarEstadoEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idEmpleado = Integer.parseInt(request.getParameter("id"));
        String nuevoEstado = request.getParameter("estado");

        Empleado empleado = empleadoDAO.obtenerEmpleado(idEmpleado);
        if (empleado == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Empleado no encontrado");
            return;
        }

        Estado estado = Estado.valueOf(nuevoEstado);
        empleadoDAO.cambiarEstadoEmpleado(idEmpleado, estado);

        String mensaje = estado == Estado.ACTIVO ?
                "Empleado reactivado exitosamente" : "Empleado inactivado exitosamente";

        request.getSession().setAttribute("mensajeExito", mensaje);
        response.sendRedirect(request.getContextPath() + "/empleados");
    }

    private void mapearParametrosEmpleado(HttpServletRequest request, Empleado empleado) {
        empleado.setNombre(request.getParameter("nombre"));
        empleado.setDocumentoIdentificacion(request.getParameter("documentoIdentificacion"));
        empleado.setTipoPersona(TipoPersona.valueOf(request.getParameter("tipoPersona")));
        empleado.setTipoContratacion(TipoContratacion.valueOf(request.getParameter("tipoContratacion")));
        empleado.setTelefono(request.getParameter("telefono"));
        empleado.setCorreo(request.getParameter("correo"));
        empleado.setDireccion(request.getParameter("direccion"));
        empleado.setEstado(Estado.valueOf(request.getParameter("estado")));
    }

    private String obtenerUsuarioSesion(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("username");
    }
}