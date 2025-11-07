package com.example.plantilla.daos;

import com.example.plantilla.beans.Usuario;
import com.example.plantilla.dtos.ReservaListadoDTO;
import com.example.plantilla.dtos.TicketListadoDTO;

import java.sql.*;
import java.util.ArrayList;

public class ReservaDao extends DaoBase{

    public ArrayList<ReservaListadoDTO> listarReservas() {

        ArrayList<ReservaListadoDTO> lista = new ArrayList<>();


        String sql = "SELECT e.titulo AS titulo_evento, e.fecha AS fecha_evento, u.nombres, u.apellidos, " +
                "       u.email, tt.nombre AS nombre_ticket, ri.cantidad AS cantidad_reservas, ri.id_item " +
                "FROM reserva_item ri " +
                "JOIN usuario u ON (ri.id_usuario = u.id_usuario) " +
                "JOIN ticket_tipo tt ON (ri.id_ticket_tipo = tt.id_ticket_tipo) " +
                "JOIN evento e ON (tt.id_evento = e.id_evento)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ReservaListadoDTO dto = new ReservaListadoDTO();
                dto.setTituloEvento(rs.getString("titulo_evento"));
                dto.setFechaEvento(rs.getString("fecha_evento"));

                Usuario usuario = new Usuario();
                usuario.setNombres(rs.getString("nombres"));
                usuario.setApellidos(rs.getString("apellidos"));
                usuario.setEmail(rs.getString("email"));
                dto.setUsuario(usuario);

                dto.setNombreTicket(rs.getString("nombre_ticket"));
                dto.setCantidadReservas(rs.getInt("cantidad_reservas"));
                dto.setIdItem(rs.getInt("id_item"));

                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void crearReserva(int idUsuario, int idTicketTipo, int cantidad) {

        String sql = "INSERT INTO reserva_item (id_usuario, id_ticket_tipo, cantidad) VALUES (?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.setInt(2, idTicketTipo);
            ps.setInt(3, cantidad);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void borrarReserva(int idItem) {

        String sql = "DELETE FROM reserva_item WHERE id_item = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idItem);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
