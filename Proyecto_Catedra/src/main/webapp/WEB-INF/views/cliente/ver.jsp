<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 26/5/2025
  Time: 16:29
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
    .detail-card {
        background: #f8f9fa;
        border-radius: 15px;
        padding: 20px;
        margin-bottom: 15px;
        border: 2px solid #e2e8f0;
    }
    .detail-label {
        font-weight: bold;
        color: #4a5568;
        margin-bottom: 5px;
    }
    .detail-value {
        background: white;
        padding: 10px 15px;
        border-radius: 20px;
        border: 1px solid #e2e8f0;
    }
    .btn-custom {
        border-radius: 25px;
        padding: 12px 30px;
        font-weight: bold;
        margin: 5px;
    }
    .btn-edit {
        background: #ffd93d;
        border: none;
        color: #333;
    }
    .btn-delete {
        background: #ff6b6b;
        border: none;
        color: white;
    }
    .btn-back {
        background: #4facfe;
        border: none;
        color: white;
    }
</style>

<div class="container-fluid">
    <div class="detail-title">
        CLIENTE
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="detail-card">
                <div class="detail-label">ID</div>
                <div class="detail-value">${cliente.idCliente}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">NOMBRE DEL CLIENTE</div>
                <div class="detail-value">${cliente.nombre}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">TIPO DE PERSONA</div>
                <div class="detail-value">${cliente.tipoPersona}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">CORREO ELECTRÓNICO</div>
                <div class="detail-value">${cliente.correo}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">ESTADO</div>
                <div class="detail-value">${cliente.estado}</div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="detail-card">
                <div class="detail-label">TELÉFONO</div>
                <div class="detail-value">${cliente.telefono}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">FECHA DE CREACIÓN</div>
                <div class="detail-value">${cliente.fechaCreacion}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">CREADO POR</div>
                <div class="detail-value">${cliente.creadoPor}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">FECHA DE ACTUALIZACIÓN</div>
                <div class="detail-value">${cliente.fechaActualizacion}</div>
            </div>

            <div class="detail-card">
                <div class="detail-label">FECHA DE INACTIVACIÓN</div>
                <div class="detail-value">${cliente.fechaInactivacion}</div>
            </div>
        </div>
    </div>

    <div class="text-center mt-4">
        <a href="clientes?action=editar&id=${cliente.idCliente}" class="btn btn-custom btn-edit">EDITAR</a>
        <a href="clientes?action=eliminar&id=${cliente.idCliente}" class="btn btn-custom btn-delete" onclick="return confirm('¿Está seguro de eliminar este cliente?')">ELIMINAR</a>
        <a href="clientes" class="btn btn-custom btn-back">VOLVER</a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
