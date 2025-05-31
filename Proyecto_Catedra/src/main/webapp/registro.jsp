<%--
  Created by IntelliJ IDEA.
  User: marvi
  Date: 27/5/2025
  Time: 08:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Multi-Works Group - Registro</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Arial', sans-serif;
            padding: 20px 0;
        }
        .register-container {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            backdrop-filter: blur(10px);
            max-width: 800px;
            width: 100%;
            margin: 20px;
        }
        .logo {
            text-align: center;
            margin-bottom: 30px;
        }
        .logo h1 {
            color: #4a5568;
            font-weight: bold;
            margin: 0;
            font-size: 24px;
        }
        .logo h2 {
            color: #667eea;
            font-weight: bold;
            margin: 0;
            font-size: 18px;
        }
        .register-title {
            background: linear-gradient(135deg, #4facfe, #00f2fe);
            color: white;
            text-align: center;
            padding: 15px;
            border-radius: 10px;
            margin-bottom: 30px;
            font-weight: bold;
        }
        .form-control, .form-select {
            border-radius: 25px;
            padding: 12px 20px;
            border: 2px solid #e2e8f0;
            margin-bottom: 15px;
        }
        .form-control:focus, .form-select:focus {
            border-color: #4facfe;
            box-shadow: 0 0 0 0.2rem rgba(79, 172, 254, 0.25);
        }
        .btn-register {
            background: linear-gradient(135deg, #4facfe, #00f2fe);
            border: none;
            border-radius: 25px;
            padding: 12px 30px;
            color: white;
            font-weight: bold;
            transition: transform 0.2s;
            margin: 5px;
        }
        .btn-register:hover {
            transform: translateY(-2px);
            color: white;
        }
        .btn-cancel {
            background: linear-gradient(135deg, #ff6b6b, #ee5a52);
            border: none;
            border-radius: 25px;
            padding: 12px 30px;
            color: white;
            font-weight: bold;
            transition: transform 0.2s;
            margin: 5px;
            text-decoration: none;
            display: inline-block;
        }
        .btn-cancel:hover {
            transform: translateY(-2px);
            color: white;
            text-decoration: none;
        }
        .gear-icon {
            width: 40px;
            height: 40px;
            margin: 0 auto 10px;
            background: #4facfe;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 20px;
        }
        .section-title {
            color: #4a5568;
            font-weight: bold;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #e2e8f0;
        }
        .required {
            color: #ff6b6b;
        }
        .password-strength {
            font-size: 12px;
            margin-top: -10px;
            margin-bottom: 15px;
        }
        .strength-weak { color: #ff6b6b; }
        .strength-medium { color: #ffd93d; }
        .strength-strong { color: #4facfe; }
    </style>
</head>
<body>
<div class="register-container">
    <div class="logo">
        <div class="gear-icon">⚙</div>
        <h1>MULTI-WORKS</h1>
        <h2>GROUP</h2>
    </div>

    <div class="register-title">
        CREAR NUEVA CUENTA
    </div>

    <form action="auth" method="post" id="registerForm">
        <input type="hidden" name="action" value="registro">

        <!-- Información Personal -->
        <div class="section-title">
            <h5>📋 Información Personal <span class="required">*</span></h5>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <input type="text" class="form-control" name="nombre" placeholder="NOMBRE COMPLETO *" required>
                </div>

                <div class="mb-3">
                    <select class="form-select" name="tipoPersona" required>
                        <option value="">TIPO DE PERSONA *</option>
                        <option value="Natural">Natural</option>
                        <option value="Jurídica">Jurídica</option>
                    </select>
                </div>

                <div class="mb-3">
                    <input type="tel" class="form-control" name="telefono" placeholder="TELÉFONO *" required pattern="[0-9]{10}" title="Ingrese un número de teléfono válido de 10 dígitos">
                </div>
            </div>

            <div class="col-md-6">
                <div class="mb-3">
                    <input type="email" class="form-control" name="correo" placeholder="CORREO ELECTRÓNICO *" required>
                </div>

                <div class="mb-3">
                    <input type="text" class="form-control" name="documento" placeholder="DOCUMENTO DE IDENTIFICACIÓN">
                </div>

                <div class="mb-3">
                    <input type="text" class="form-control" name="direccion" placeholder="DIRECCIÓN">
                </div>
            </div>
        </div>

        <!-- Información de Acceso -->
        <div class="section-title">
            <h5>🔐 Información de Acceso <span class="required">*</span></h5>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <input type="text" class="form-control" name="usuario" placeholder="NOMBRE DE USUARIO *" required minlength="4" pattern="[a-zA-Z0-9_]+" title="Solo letras, números y guiones bajos">
                </div>

                <div class="mb-3">
                    <input type="password" class="form-control" name="contrasena" id="password" placeholder="CONTRASEÑA *" required minlength="6">
                    <div class="password-strength" id="passwordStrength"></div>
                </div>
            </div>

            <div class="col-md-6">
                <div class="mb-3">
                    <input type="password" class="form-control" name="confirmarContrasena" id="confirmPassword" placeholder="CONFIRMAR CONTRASEÑA *" required minlength="6">
                    <div class="password-match" id="passwordMatch"></div>
                </div>

                <div class="mb-3">
                    <select class="form-select" name="estado">
                        <option value="Activo" selected>ESTADO: ACTIVO</option>
                        <option value="Inactivo">ESTADO: INACTIVO</option>
                    </select>
                </div>
            </div>
        </div>

        <!-- Información del Sistema -->
        <div class="section-title">
            <h5>⚙️ Información del Sistema</h5>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="mb-3">
                    <input type="date" class="form-control" name="fechaCreacion" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>" readonly>
                </div>
            </div>

            <div class="col-md-6">
                <div class="mb-3">
                    <input type="text" class="form-control" name="creadoPor" value="Sistema - Registro Automático" readonly>
                </div>
            </div>
        </div>

        <!-- Términos y Condiciones -->
        <div class="row">
            <div class="col-12">
                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="terminos" name="terminos" required>
                    <label class="form-check-label" for="terminos">
                        Acepto los <a href="#" data-bs-toggle="modal" data-bs-target="#terminosModal">términos y condiciones</a> <span class="required">*</span>
                    </label>
                </div>

                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="politicas" name="politicas" required>
                    <label class="form-check-label" for="politicas">
                        Acepto las <a href="#" data-bs-toggle="modal" data-bs-target="#politicasModal">políticas de privacidad</a> <span class="required">*</span>
                    </label>
                </div>
            </div>
        </div>

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-register" id="submitBtn" disabled>CREAR CUENTA</button>
            <a href="index.jsp" class="btn btn-cancel">VOLVER AL LOGIN</a>
        </div>
    </form>

    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3" role="alert">
            <strong>Error:</strong> ${error}
        </div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="alert alert-success mt-3" role="alert">
            <strong>¡Éxito!</strong> ${success}
            <br><a href="index.jsp" class="alert-link">Ir al inicio de sesión</a>
        </div>
    </c:if>
</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cerrar</button>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Validación de fortaleza de contraseña
    function checkPasswordStrength(password) {
        let strength = 0;
        let feedback = '';

        if (password.length >= 8) strength++;
        if (password.match(/[a-z]/)) strength++;
        if (password.match(/[A-Z]/)) strength++;
        if (password.match(/[0-9]/)) strength++;
        if (password.match(/[^a-zA-Z0-9]/)) strength++;

        switch(strength) {
            case 0:
            case 1:
                feedback = '<span class="strength-weak">Contraseña muy débil</span>';
                break;
            case 2:
            case 3:
                feedback = '<span class="strength-medium">Contraseña media</span>';
                break;
            case 4:
            case 5:
                feedback = '<span class="strength-strong">Contraseña fuerte</span>';
                break;
        }

        return { strength, feedback };
    }

    // Eventos de validación
    document.getElementById('password').addEventListener('input', function() {
        const result = checkPasswordStrength(this.value);
        document.getElementById('passwordStrength').innerHTML = result.feedback;
        checkFormValidity();
    });

    document.getElementById('confirmPassword').addEventListener('input', function() {
        const password = document.getElementById('password').value;
        const confirmPassword = this.value;
        const matchDiv = document.getElementById('passwordMatch');

        if (confirmPassword.length > 0) {
            if (password === confirmPassword) {
                matchDiv.innerHTML = '<span class="strength-strong">Las contraseñas coinciden</span>';
                this.style.borderColor = '#4facfe';
            } else {
                matchDiv.innerHTML = '<span class="strength-weak">Las contraseñas no coinciden</span>';
                this.style.borderColor = '#ff6b6b';
            }
        } else {
            matchDiv.innerHTML = '';
            this.style.borderColor = '#e2e8f0';
        }
        checkFormValidity();
    });

    // Verificar validez del formulario
    function checkFormValidity() {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;
        const terminos = document.getElementById('terminos').checked;
        const politicas = document.getElementById('politicas').checked;
        const submitBtn = document.getElementById('submitBtn');

        const passwordsMatch = password === confirmPassword && password.length >= 6;
        const checkboxesChecked = terminos && politicas;

        if (passwordsMatch && checkboxesChecked) {
            submitBtn.disabled = false;
            submitBtn.style.opacity = '1';
        } else {
            submitBtn.disabled = true;
            submitBtn.style.opacity = '0.6';
        }
    }

    // Eventos para checkboxes
    document.getElementById('terminos').addEventListener('change', checkFormValidity);
    document.getElementById('politicas').addEventListener('change', checkFormValidity);

    // Validación final del formulario
    document.getElementById('registerForm').addEventListener('submit', function(e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password !== confirmPassword) {
            e.preventDefault();
            alert('Las contraseñas no coinciden. Por favor, verifique e intente nuevamente.');
            return false;
        }

        if (password.length < 6) {
            e.preventDefault();
            alert('La contraseña debe tener al menos 6 caracteres.');
            return false;
        }

        // Mostrar indicador de carga
        const submitBtn = document.getElementById('submitBtn');
        submitBtn.innerHTML = 'CREANDO CUENTA...';
        submitBtn.disabled = true;
    });

    // Validación de usuario en tiempo real
    document.querySelector('input[name="usuario"]').addEventListener('input', function() {
        const usuario = this.value;
        const regex = /^[a-zA-Z0-9_]+$/;

        if (usuario.length > 0 && !regex.test(usuario)) {
            this.style.borderColor = '#ff6b6b';
        } else {
            this.style.borderColor = '#4facfe';
        }
    });

    // Inicializar validación
    checkFormValidity();
</script>
</body>
</html>
