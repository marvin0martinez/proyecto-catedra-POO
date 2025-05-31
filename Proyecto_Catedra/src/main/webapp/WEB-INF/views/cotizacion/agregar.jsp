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
    .btn-assign {
        background: #ffd93d;
        border: none;
        color: #333;
    }
</style>

<div class="container-fluid">
    <div class="form-title">
        AGREGAR COTIZACIÓN
    </div>

    <form action="cotizaciones" method="post">
        <input type="hidden" name="action" value="guardar">

        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <input type="text" class="form-control" name="id" placeholder="ID" readonly>
                </div>

                <div class="mb-3">
                    <select class="form-control" name="cliente" required>
                        <option value="">CLIENTE</option>
                        <c:forEach var="cliente" items="${clientes}">
                            <option value="${cliente.idCliente}">${cliente.nombre}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <input type="number" class="form-control" name="cantidadHoras" placeholder="CANTIDAD DE HORAS" required>
                </div>

                <div class="mb-3">
                    <input type="number" step="0.01" class="form-control" name="costoAdicionales" placeholder="COSTO ADICIONALES" required>
                </div>

                <div class="mb-3">
                    <input type="date" class="form-control" name="fechaInicio" placeholder="FECHA INICIO" required>
                </div>
            </div>

            <div class="col-md-6">
                <div class="mb-3">
                    <input type="date" class="form-control" name="fechaFin" placeholder="FECHA FIN" required>
                </div>

                <div class="mb-3">
                    <input type="number" step="0.01" class="form-control" name="total" placeholder="TOTAL" readonly>
                </div>
            </div>
        </div>

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-custom btn-save">GUARDAR</button>
            <button type="button" class="btn btn-custom btn-assign" onclick="location.href='asignaciones?action=agregar'">ASIGNACIÓN</button>
            <a href="cotizaciones" class="btn btn-custom btn-cancel">CANCELAR</a>
        </div>
    </form>
</div>

<script>
    // Calcular total automáticamente
    document.addEventListener('DOMContentLoaded', function() {
        const cantidadHoras = document.querySelector('input[name="cantidadHoras"]');
        const costoAdicionales = document.querySelector('input[name="costoAdicionales"]');
        const total = document.querySelector('input[name="total"]');

        function calcularTotal() {
            const horas = parseFloat(cantidadHoras.value) || 0;
            const adicionales = parseFloat(costoAdicionales.value) || 0;
            const costoHora = 50000; // Valor por defecto
            total.value = (horas * costoHora + adicionales).toFixed(2);
        }

        cantidadHoras.addEventListener('input', calcularTotal);
        costoAdicionales.addEventListener('input', calcularTotal);
    });
</script>

<jsp:include page="/WEB-INF/views/templates/footer.jsp"/>
