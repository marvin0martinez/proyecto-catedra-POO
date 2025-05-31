<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 26/5/2025
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/templates/header.jsp"/>
<jsp:include page="/WEB-INF/views/templates/menu.jsp"/>

<style>
  .form-title {
    background: linear-gradient(135deg, #667eea, #764ba2);
    color: white;
    text-align: center;
    padding: 15px;
    border-radius: 10px;
    margin-bottom: 30px;
    font-weight: bold;
  }
  .form-control {
    border-radius: 25px;
    padding: 12px 20px;
    border: 2px solid #e2e8f0;
    margin-bottom: 15px;
  }
  .form-control:focus {
    border-color: #667eea;
    box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
  }
  .btn-custom {
    border-radius: 25px;
    padding: 12px 30px;
    font-weight: bold;
    margin: 5px;
  }
  .btn-save {
    background: #4facfe;
    border: none;
    color: white;
  }
  .btn-cancel {
    background: #ff6b6b;
    border: none;
    color: white;
  }
</style>

<div class="container-fluid">
  <div class="form-title">
    EDITAR CLIENTE
  </div>

  <form action="clientes" method="post">
    <input type="hidden" name="action" value="actualizar">
    <input type="hidden" name="id" value="${cliente.idCliente}">

    <div class="row">
      <div class="col-md-6">
        <div class="mb-3">
          <input type="text" class="form-control" name="id" value="${cliente.idCliente}" placeholder="ID" readonly>
        </div>

        <div class="mb-3">
          <input type="text" class="form-control" name="nombre" value="${cliente.nombre}" placeholder="NOMBRE DEL CLIENTE" required>
        </div>

        <div class="mb-3">
          <select class="form-control" name="tipoPersona" required>
            <option value="">TIPO DE PERSONA</option>
            <option value="Natural" ${cliente.tipoPersona == 'Natural' ? 'selected' : ''}>Natural</option>
            <option value="Jurídica" ${cliente.tipoPersona == 'Jurídica' ? 'selected' : ''}>Jurídica</option>
          </select>
        </div>

        <div class="mb-3">
          <input type="email" class="form-control" name="correo" value="${cliente.correo}" placeholder="CORREO ELECTRÓNICO" required>
        </div>

        <div class="mb-3">
          <select class="form-control" name="estado" required>
            <option value="">ESTADO</option>
            <option value="Activo" ${cliente.estado == 'Activo' ? 'selected' : ''}>Activo</option>
            <option value="Inactivo" ${cliente.estado == 'Inactivo' ? 'selected' : ''}>Inactivo</option>
          </select>
        </div>
      </div>

      <div class="col-md-6">
        <div class="mb-3">
          <input type="tel" class="form-control" name="telefono" value="${cliente.telefono}" placeholder="TELÉFONO" required>
        </div>

        <div class="mb-3">
          <input type="date" class="form-control" name="fechaCreacion" value="${cliente.fechaCreacion}" placeholder="FECHA DE CREACIÓN" required>
        </div>

        <div class="mb-3">
          <input type="text" class="form-control" name="creadoPor" value="${cliente.creadoPor}" placeholder="CREADO POR" required>
        </div>

        <div class="mb-3">
          <input type="date" class="form-control" name="fechaActualizacion" value="${cliente.fechaActualizacion}" placeholder="FECHA DE ACTUALIZACIÓN">
        </div>

        <div class="mb-3">
          <input type="date" class="form-control" name="fechaInactivacion" value="${cliente.fechaInactivacion}" placeholder="FECHA DE INACTIVACIÓN">
        </div>
      </div>
    </div>

    <div class="text-center mt-4">
      <button type="submit" class="btn btn-custom btn-save">GUARDAR</button>
      <a href="clientes" class="btn btn-custom btn-cancel">CANCELAR</a>
    </div>
  </form>
</div>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
