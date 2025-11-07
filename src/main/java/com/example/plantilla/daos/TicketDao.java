package com.example.plantilla.daos;

import com.example.plantilla.beans.TicketTipo;
import com.example.plantilla.dtos.TicketListadoDTO;

import java.sql.*;
import java.util.ArrayList;

public class TicketDao extends DaoBase{

    public ArrayList<TicketListadoDTO> listarTickets() {

        ArrayList<TicketListadoDTO> lista = new ArrayList<>();

        String sql = "SELECT e.titulo, e.descripcion, e.fecha, e.lugar, " +
                "       t.nombre, t.precio, t.id_ticket_tipo, " +
                "       t.cupo_total, " +
                "       SUM(ri.cantidad) AS total_reservado " +
                "FROM ticket_tipo t " +
                "JOIN evento e ON t.id_evento = e.id_evento " +
                "LEFT JOIN reserva_item ri ON t.id_ticket_tipo = ri.id_ticket_tipo " +
                "GROUP BY t.id_ticket_tipo, e.id_evento";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TicketListadoDTO dto = new TicketListadoDTO();
                dto.setTituloEvento(rs.getString("titulo"));
                dto.setDescripcionEvento(rs.getString("descripcion"));
                dto.setFechaEvento(rs.getString("fecha"));
                dto.setLugarEvento(rs.getString("lugar"));
                dto.setNombreTicket(rs.getString("nombre"));
                dto.setPrecio(rs.getDouble("precio"));
                dto.setIdTicketTipo(rs.getInt("id_ticket_tipo"));

                int cupoTotal = rs.getInt("cupo_total");
                int totalReservado = rs.getInt("total_reservado");
                dto.setCupoDisponible(cupoTotal - totalReservado);

                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void crearTicket(int idEvento, String nombre, double precio, int cupoTotal, int cupoDisponible) {

        String sql = "INSERT INTO ticket_tipo (id_evento, nombre, precio, cupo_total, cupo_disponible) VALUES (?,?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idEvento);
            ps.setString(2, nombre);
            ps.setDouble(3, precio);
            ps.setInt(4, cupoTotal);
            ps.setInt(5, cupoDisponible);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrarTicket(int idTicketTipo) {

        try (Connection conn = this.getConnection();) {
            String sql = "DELETE  FROM ticket_tipo WHERE id_ticket_tipo = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idTicketTipo);
                pstmt.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public TicketTipo ObtenerTicketporID(int idTicketTipo) {

        TicketTipo ticket = null;
        String sql = "SELECT t.id_ticket_tipo, t.id_evento, t.nombre, t.precio, t.cupo_total, " +
                "       SUM(ri.cantidad) AS total_reservado " +
                "FROM ticket_tipo t " +
                "LEFT JOIN reserva_item ri ON t.id_ticket_tipo = ri.id_ticket_tipo " +
                "WHERE t.id_ticket_tipo = ? " +
                "GROUP BY t.id_ticket_tipo";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idTicketTipo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ticket = new TicketTipo();
                    ticket.setId_ticket_tipo(rs.getInt("id_ticket_tipo"));
                    ticket.setId_evento(rs.getInt("id_evento"));
                    ticket.setNombre(rs.getString("nombre"));
                    ticket.setPrecio(rs.getDouble("precio"));
                    ticket.setCupo_total(rs.getInt("cupo_total"));

                    int cupoTotal = rs.getInt("cupo_total");
                    int totalReservado = rs.getInt("total_reservado");
                    ticket.setCupo_disponible(cupoTotal - totalReservado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ticket;
    }

    public ArrayList<TicketTipo> listarTiposDeTicket(int idEvento) {

        ArrayList<TicketTipo> lista = new ArrayList<>();
        String sql = "SELECT t.id_ticket_tipo, t.id_evento, t.nombre, t.precio, t.cupo_total, " +
                "       SUM(ri.cantidad) AS total_reservado " +
                "FROM ticket_tipo t " +
                "LEFT JOIN reserva_item ri ON t.id_ticket_tipo = ri.id_ticket_tipo " +
                "WHERE t.id_evento = ? " +
                "GROUP BY t.id_ticket_tipo " +
                "ORDER BY t.nombre";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvento);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TicketTipo ticket = new TicketTipo();
                    ticket.setId_ticket_tipo(rs.getInt("id_ticket_tipo"));
                    ticket.setId_evento(rs.getInt("id_evento"));
                    ticket.setNombre(rs.getString("nombre"));
                    ticket.setPrecio(rs.getDouble("precio"));
                    ticket.setCupo_total(rs.getInt("cupo_total"));

                    int cupoTotal = rs.getInt("cupo_total");
                    int totalReservado = rs.getInt("total_reservado");
                    ticket.setCupo_disponible(cupoTotal - totalReservado);
                    lista.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
