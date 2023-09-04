
package com.alura.dao;

import com.alura.models.Reserva;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaDao {
    private Connection connection;
    public ReservaDao(Connection connection) {
        this.connection = connection;
    }
    public int guardar(Reserva reserva) {

        int generatedId = -1;

        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO reservas (huesped_id, fecha_ingreso, fecha_egreso, valor, metodo_pago)"
                + " VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            

            statement.setInt(1, reserva.getHuespedId());
            statement.setDate(2, java.sql.Date.valueOf(reserva.getFechaIngreso()));
            statement.setDate(3, java.sql.Date.valueOf(reserva.getFechaEgreso()));
            statement.setDouble(4, reserva.getValorTotal());
            statement.setString(5, reserva.getMetodoPago());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 1) {
                try (ResultSet resultSet =statement.getGeneratedKeys()) {

                    while (resultSet.next()) {

                        reserva.setId(resultSet.getInt(1));
                    }
                }
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

        return reserva.getId();
    }

    public List<Reserva> listar() {
        List<Reserva> r = new ArrayList<>();


        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservas")) {

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int huespedId = resultSet.getInt("huesped_id");
                    Date fechaIngreso = resultSet.getDate("fecha_ingreso");
                    Date fechaEgreso = resultSet.getDate("fecha_egreso");
                    Double valorTotal = resultSet.getDouble("valor");
                    String metodoPago = resultSet.getString("metodo_pago");


                    Reserva reserva = new Reserva(id, huespedId, fechaIngreso.toLocalDate(), fechaEgreso.toLocalDate(), valorTotal, metodoPago);


                    r.add(reserva);
                }
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

        return r;
    }

    public int eliminar(int id) {

        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM reservas WHERE id = ?")) {

            statement.setInt(1, id);

            int rowsAffected = statement.executeUpdate();

            return rowsAffected;
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public List<Reserva> listarPorHuespedId(int huespedId) {
        List<Reserva> r = new ArrayList<>();


        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM reservas WHERE huesped_id = ?")) {

            statement.setInt(1, huespedId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    Date fechaIngreso = resultSet.getDate("fecha_ingreso");
                    Date fechaEgreso = resultSet.getDate("fecha_egreso");
                    Double valorTotal = resultSet.getDouble("valor_total");
                    String metodoPago = resultSet.getString("metodo_pago");

                    Reserva reserva = new Reserva(id, huespedId, fechaIngreso.toLocalDate(), fechaEgreso.toLocalDate(), valorTotal, metodoPago);

                    r.add(reserva);
                }
            }
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }

        return r;
    }

    public int modificar(LocalDate fechaIngreso, LocalDate fechaEgreso, String formaDePago, int id) {
        
        Reserva reserva = new Reserva();
        
        int dias = fechaEgreso.getDayOfYear() - fechaIngreso.getDayOfYear();
        double valorTotal = dias * reserva.getPrecioNoche();
        try (PreparedStatement statement = connection.prepareStatement("UPDATE reservas SET fecha_ingreso = ?, fecha_egreso = ?, valor_total = ?,metodo_pago = ? WHERE id = ?")) {

            statement.setDate(1, java.sql.Date.valueOf(fechaIngreso));
            statement.setDate(2, java.sql.Date.valueOf(fechaEgreso));
            statement.setDouble(3, valorTotal);
            statement.setString(4, formaDePago);
            statement.setInt(5, id);    


            int rowsAffected = statement.executeUpdate();

            return rowsAffected;
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

}
