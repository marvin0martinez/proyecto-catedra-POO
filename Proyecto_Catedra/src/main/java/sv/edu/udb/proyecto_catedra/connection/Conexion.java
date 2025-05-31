package sv.edu.udb.proyecto_catedra.connection;

import java.sql.*;

public class Conexion {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/proyecto_catedra?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";
    private static Connection conexion = null;
    public static synchronized Connection getConnection() {
        if (conexion == null) {
            try {
                Class.forName(JDBC_DRIVER);
                // Establecer la conexión
                conexion = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
                System.out.println("Conexión exitosa a la base de datos.");
            } catch (ClassNotFoundException e) {
                System.err.println("Error: No se encontró el driver JDBC.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Error conectando a la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return conexion;
    }
    public static void closeConnection() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

