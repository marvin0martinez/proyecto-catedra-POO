<%--
  Created by IntelliJ IDEA.
  User: MINEDUCYT
  Date: 14/05/2025
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}">Inicio</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/clientes">Clientes</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/empleados">Empleados</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/cotizaciones">Cotizaciones</a>
                </li>
                <c:if test="${not empty sessionScope.username}">
                    <li class="nav-item ms-auto">
                        <span class="nav-link">Bienvenido, ${sessionScope.username}</span>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/logout">Cerrar Sesión</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
