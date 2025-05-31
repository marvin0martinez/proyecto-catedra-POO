<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Multi-Works Group - Inicio de Sesión</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Arial', sans-serif;
        }
        .login-container {
            background: rgba(255, 255, 255, 0.95);
            border-radius: 20px;
            padding: 40px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
            backdrop-filter: blur(10px);
            max-width: 400px;
            width: 100%;
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
        .login-title {
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
            margin-bottom: 20px;
        }
        .form-control:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
        }
        .btn-register {
            background: linear-gradient(135deg, #4facfe, #00f2fe);
            border: none;
            border-radius: 25px;
            padding: 12px 30px;
            color: white;
            font-weight: bold;
            width: 100%;
            transition: transform 0.2s;
            text-decoration: none;
            display: block;
            text-align: center;
        }
        .btn-register:hover {
            transform: translateY(-2px);
            color: white;
            text-decoration: none;
        }
        .btn-login {
            background: linear-gradient(135deg, #667eea, #764ba2);
            border: none;
            border-radius: 25px;
            padding: 12px 30px;
            color: white;
            font-weight: bold;
            width: 100%;
            transition: transform 0.2s;
        }
        .btn-login:hover {
            transform: translateY(-2px);
            color: white;
        }
        .gear-icon {
            width: 40px;
            height: 40px;
            margin: 0 auto 10px;
            background: #667eea;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 20px;
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="logo">
        <div class="gear-icon">⚙</div>
        <h1>MULTI-WORKS</h1>
        <h2>GROUP</h2>
    </div>

    <div class="login-title">
        INICIO SESIÓN
    </div>

    <form action="auth" method="post">
        <input type="hidden" name="action" value="login">

        <div class="mb-3">
            <input type="text" class="form-control" name="usuario" placeholder="USUARIO" required>
        </div>

        <div class="mb-3">
            <input type="password" class="form-control" name="contrasena" placeholder="CONTRASEÑA" required>
        </div>

        <button type="submit" class="btn btn-login">INICIAR SESIÓN</button>
    </form>

    <div class="divider">
        <span>¿No tienes cuenta?</span>
    </div>

    <a href="registro.jsp" class="btn btn-register">CREAR CUENTA</a>

    <c:if test="${not empty error}">
        <div class="alert alert-danger mt-3" role="alert">
                ${error}
        </div>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>