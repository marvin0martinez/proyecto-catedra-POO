<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 26/5/2025
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/templates/header.jsp"/>
<jsp:include page="/WEB-INF/views/templates/menu.jsp"/>

<style>
    .dashboard-title {
        background: linear-gradient(135deg, #667eea, #764ba2);
        color: white;
        text-align: center;
        padding: 20px;
        border-radius: 15px;
        margin-bottom: 30px;
        font-weight: bold;
        font-size: 24px;
    }
    .stats-container {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
        gap: 20px;
        margin-bottom: 30px;
    }
    .stat-card {
        background: linear-gradient(135deg, #a8edea, #fed6e3);
        border-radius: 15px;
        padding: 25px;
        text-align: center;
        transition: transform 0.3s;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    }
    .stat-card:hover {
        transform: translateY(-5px);
    }
    .stat-number {
        font-size: 36px;
        font-weight: bold;
        color: #333;
        margin-bottom: 10px;
    }
    .stat-label {
        font-size: 16px;
        color: #666;
        font-weight: 500;
    }
    .quick-actions {
        background: white;
        border-radius: 15px;
        padding: 25px;
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
    }
    .quick-actions h4 {
        color: #333;
        margin-bottom: 20px;
        font-weight: bold;
    }
    .action-btn {
        background: linear-gradient(135deg, #4facfe, #00f2fe);
        border: none;
        color: white;
        padding: 12px 25px;
        border-radius: 25px;
        margin: 5px;
        text-decoration: none;
        display: inline-block;
        font-weight: bold;
        transition: transform 0.2s;
    }
    .action-btn:hover {
        transform: translateY(-2px);
        color: white;
        text-decoration: none;
    }
    .welcome-section {
        background: linear-gradient(135deg, #ffd93d, #ff9a9e);
        border-radius: 15px;
        padding: 25px;
        margin-bottom: 30px;
        text-align: center;
    }
    .welcome-section h2 {
        color: #333;
        font-weight: bold;
        margin-bottom: 10px;
    }
    .welcome-section p {
        color: #666;
        margin: 0;
    }
</style>

<div class="container-fluid">
    <div class="dashboard-title">
        PANEL DE CONTROL - MULTI-WORKS GROUP
    </div>

    <div class="welcome-section">
        <h2>¡Bienvenido al Sistema de Gestión!</h2>
        <p>Administra clientes, empleados, cotizaciones y asignaciones desde un solo lugar</p>
    </div>

    <div class="stats-container">
        <div class="stat-card">
            <div class="stat-number">${totalClientes != null ? totalClientes : 0}</div>
            <div class="stat-label">CLIENTES REGISTRADOS</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${totalEmpleados != null ? totalEmpleados : 0}</div>
            <div class="stat-label">EMPLEADOS ACTIVOS</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${totalCotizaciones != null ? totalCotizaciones : 0}</div>
            <div class="stat-label">COTIZACIONES</div>
        </div>
        <div class="stat-card">
            <div class="stat-number">${totalAsignaciones != null ? totalAsignaciones : 0}</div>
            <div class="stat-label">ASIGNACIONES</div>
        </div>
    </div>

    <div class="quick-actions">
        <h4>ACCIONES RÁPIDAS</h4>
        <a href="${pageContext.request.contextPath}/clientes?action=agregar" class="action-btn">
            + NUEVO CLIENTE
        </a>
        <a href="${pageContext.request.contextPath}/empleados?action=agregar" class="action-btn">
            + NUEVO EMPLEADO
        </a>
        <a href="${pageContext.request.contextPath}/cotizaciones?action=agregar" class="action-btn">
            + NUEVA COTIZACIÓN
        </a>
        <a href="${pageContext.request.contextPath}/asignaciones?action=agregar" class="action-btn">
            + NUEVA ASIGNACIÓN
        </a>
        <a href="${pageContext.request.contextPath}/subtareas?action=agregar" class="action-btn">
            + NUEVA SUBTAREA
        </a>
    </div>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>

