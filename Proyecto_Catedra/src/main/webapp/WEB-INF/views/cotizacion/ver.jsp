<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 26/5/2025
  Time: 16:39
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
    .cotizacion-table {
        background: white;
        border-radius: 15px;
        overflow: hidden;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        margin-bottom: 30px;
    }
    .table-header {
        background: linear-gradient(135deg, #4facfe, #00f2fe);
        color: white;
        font-weight: bold;
        text-align: center;
        padding: 15px;
    }
    .table-cell {
        padding: 15px;
        text-align: center;
        border-bottom: 1px solid #e2e8f0;
        background: #f8f9fa;
    }
    .total-section {
        background: linear-gradient(135deg, #ffd93d, #ff9a9e);
        color: #333;
        font-weight: bold;
        text-align: center;
        padding: 20px;
        border-radius: 15px;
        margin: 20px 0;
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
    .btn-assign {
        background: #ffd93d;
        border: none;
        color: #333;
    }
    .btn-delete {
        background: #ff6b6b;
        border: none;
        color: white;
    }
</style>

<div class="container-fluid">
    <div class="detail-title">
        COTIZACIÓN
    </div>

    <div class="cotizacion-table">
        <div class="row g-0">
            <div class="col-2">
                <div class="table-header">ID COTIZACIÓN</div>
                <div class="table-cell">${cotizacion.idCotizacion}</div>
            </div>
            <div class="col-2">
                <div class="table-header">CLIENTE</div>
                <div class="table-cell">${cotizacion.nombreCliente}</div>
            </div>
            <div class="col-2">
                <div class="table-header">CANTIDAD DE HORAS</div>
                <div class="table-cell">${cotizacion.cantidadHoras}</div>
            </div>
            <div class="col-2">
                <div class="table-header">FECHA INICIO</div>
                <div class="table-cell">${cotizacion.fechaInicio}</div>
            </div>
            <div class="col-2">
                <div class="table-header">FECHA FIN</div>
                <div class="table-cell">${cotizacion.fechaFin}</div>
            </div>
            <div class="col-2">
                <div class="table-header">COSTO ADICIONALES</div>
                <div class="table-cell">$${cotizacion.costoAdicionales}</div>
            </div>
        </div>
    </div>

    <div class="total-section">
        <h4>TOTAL: $${cotizacion.total}</h4>
    </div>

    <div class="text-center mt-4">
        <a href="cotizaciones?action=editar&id=${cotizacion.idCotizacion}" class="btn btn-custom btn-edit">EDITAR</a>
        <a href="asignaciones?action=ver&cotizacionId=${cotizacion.idCotizacion}" class="btn btn-custom btn-assign">VER ASIGNACIÓN</a>
        <a href="cotizaciones?action=eliminar&id=${cotizacion.idCotizacion}" class="btn btn-custom btn-delete" onclick="return confirm('¿Está seguro de eliminar esta cotización?')">ELIMINAR</a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
