
package com.alura.dao;

import com.alura.models.Huesped;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HuespedDao {
    
    private Connection conexion;
    
    public HuespedDao(Connection conexion) {
        this.conexion = conexion;
    }

    public int guardar(Huesped huesped) {

        try {
            PreparedStatement statement = conexion.prepareStatement(
                    "INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad, telefono)"
                            + " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, huesped.getNombre());
            statement.setString(2, huesped.getApellido());
            statement.setDate(3, Date.valueOf(huesped.getFechaNacimiento()));
            statement.setString(4, huesped.getNacionalidad());
            statement.setString(5, huesped.getTelefono());
            
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            
            while (resultSet.next()) {
                huesped.setId(resultSet.getInt(1));
            }
                            
            return huesped.getId();
            
        } catch (SQLException ex) {
            Logger.getLogger(HuespedDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
        
    }

    public List<Huesped> listar ()  {
        List<Huesped> huespedList = new ArrayList<>();

        try (PreparedStatement statement = conexion.prepareStatement("SELECT * FROM huespedes")) {
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String apellido = resultSet.getString("apellido");
                    Date fechaNacimiento = resultSet.getDate("fecha_nacimiento");
                    String nacionalidad = resultSet.getString("nacionalidad");
                    String telefono = resultSet.getString("telefono");

                    Huesped huesped = new Huesped(id, nombre, apellido, fechaNacimiento.toLocalDate(), nacionalidad, telefono);

                    huespedList.add(huesped);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Retornar el resultado
        return huespedList;
    }
    
    public int eliminar(Integer id) {
        try (PreparedStatement statement = conexion.prepareStatement("DELETE FROM huespedes WHERE id = ?")) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected;
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public List<Huesped> listarPorApellido(String apellido) {

        try (PreparedStatement statement = conexion.prepareStatement(
                "SELECT * FROM huespedes WHERE nombre = ? || apellido = ?")) {

                  statement.setString(1, apellido);
                  statement.setString(2, apellido);

            try (ResultSet resultSet = statement.executeQuery()) {

                List<Huesped> r = new ArrayList<>();
                while (resultSet.next()) {

                    int id = resultSet.getInt("id");
                    String nombre = resultSet.getString("nombre");
                    String apellido2 = resultSet.getString("apellido");
                    Date fechaNacimiento = resultSet.getDate("fecha_nacimiento");
                    String nacionalidad = resultSet.getString("nacionalidad");
                    String telefono = resultSet.getString("telefono");

                    Huesped huesped = new Huesped(id, nombre, apellido2, fechaNacimiento.toLocalDate(), nacionalidad, telefono);

                    r.add(huesped);
                }
                
                 System.out.println("Resultado: " + r.get(0).toString());
                
                return r;
                
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public int modificar(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad, String telefono, Integer id) {

        try (PreparedStatement statement = conexion.prepareStatement("UPDATE huespedes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, nacionalidad = ?, telefono = ? WHERE id = ?")) {

            statement.setString(1, nombre);
            statement.setString(2, apellido);
            statement.setDate(3, Date.valueOf(fechaNacimiento));
            statement.setString(4, nacionalidad);
            statement.setString(5, telefono);
            statement.setInt(6, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected;
            
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }
}
