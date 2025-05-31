<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 26/5/2025
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/templates/header.jsp"/>
<jsp:include page="/WEB-INF/views/templates/menu.jsp"/>

<style>
    .detail-title {
        background: linear-gradient(135deg, #667eea, #764ba2);
        color: white;
        text-align: center;
        padding: 15px;
        border-radius: 10px;
        margin-bottom: 30px;
        font-weight: bold;
    }
    .asignacion-table {
        background: white;
        border-radius: 15px;
        overflow: hidden;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        margin-bottom: 30px;
    }
    .table {
        margin: 0;
    }
    .table thead th {
        background: linear-gradient(135deg, #4facfe, #00f2fe);
        color: white;
        font-weight: bold;
        text-align: center;
        border: none;
        padding: 15px;
    }
    .table tbody td {
        text-align: center;
        padding: 12px;
        border-color: #e2e8f0;
        background: #f8f9fa;
    }
    .btn-custom {
        border-radius: 25px;
        padding: 12px 30px;
        font-weight: bold;
        margin: 5px;
    }
    .btn-edit {
        background: #4facfe;
        border: none;
        color: white;
    }
    .btn-delete {
        background: #ff6b6b;
        border: none;
        color: white;
    }
</style>

<div class="container-fluid">
    <div class="detail-title">
        VER ASIGNACIÓN
    </div>

    <div class="asignacion-table">
        <table class="table">
            <thead>
            <tr>
                <th>Título de la Actividad</th>
                <th>Trabajador Asignado</th>
                <th>Área Asignada</th>
                <th>Fecha y Hora de Inicio</th>
                <th>Fecha y Hora de Fin</th>
                <th>Cantidad de Horas</th>
                <th>Costo por Hora</th>
                <th>Costo Base</th>
                <th>Incremento Extra</th>
                <th>Total</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="asignacion" items="${asignaciones}">
                <tr>
                    <td>${asignacion.tituloActividad}</td>
                    <td>${asignacion.nombreTrabajador}</td>
                    <td>${asignacion.areaAsignada}</td>
                    <td>${asignacion.fechaHoraInicio}</td>
                    <td>${asignacion.fechaHoraFin}</td>
                    <td>${asignacion.cantidadHoras}</td>
                    <td>$${asignacion.costoPorHora}</td>
                    <td>$${asignacion.costoBase}</td>
                    <td>$${asignacion.incrementoExtra}</td>
                    <td><strong>$${asignacion.total}</strong></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="text-center mt-4">
        <a href="asignaciones?action=editar&id=${asignacion.idAsignacion}" class="btn btn-custom btn-edit">EDITAR</a>
        <a href="asignaciones?action=eliminar&id=${asignacion.idAsignacion}" class="btn btn-custom btn-delete" onclick="return confirm('¿Está seguro de eliminar esta asignación?')">ELIMINAR</a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
