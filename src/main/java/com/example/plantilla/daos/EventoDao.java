package com.example.plantilla.daos;

import com.example.plantilla.beans.Evento;
import com.example.plantilla.dtos.EventoListadoDTO;
import com.example.plantilla.dtos.TicketListadoDTO;

import java.sql.*;
import java.util.ArrayList;

public class EventoDao extends DaoBase{

    public ArrayList<Evento> listar() {

        ArrayList<Evento> lista = new ArrayList<>();


        String sql = "SELECT e.id_evento, e.titulo, e.descripcion, e.fecha, " +
                "       e.lugar, e.creado_en " +
                "FROM evento e ";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId_evento(rs.getInt("id_evento"));
                evento.setTitulo(rs.getString("titulo"));
                evento.setDescripcion(rs.getString("descripcion"));
                evento.setFecha(rs.getString("fecha"));
                evento.setLugar(rs.getString("lugar"));
                evento.setCreado_en(rs.getString("creado_en"));
                lista.add(evento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Evento obtieneEventoporID(int idEvento) {

        Evento evento = null;
        String sql = "SELECT * FROM evento WHERE id_evento = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvento);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    evento = new Evento();
                    evento.setId_evento(rs.getInt("id_evento"));
                    evento.setTitulo(rs.getString("titulo"));
                    evento.setDescripcion(rs.getString("descripcion"));
                    evento.setFecha(rs.getString("fecha"));
                    evento.setLugar(rs.getString("lugar"));
                    evento.setCreado_en(rs.getString("creado_en"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evento;
    }

    public ArrayList<EventoListadoDTO> listarEventosDTO() {

        ArrayList<EventoListadoDTO> lista = new ArrayList<>();
        String sql = "SELECT id_evento, titulo, descripcion, fecha, lugar FROM evento";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                EventoListadoDTO dto = new EventoListadoDTO();
                dto.setId_evento(rs.getInt(1));
                dto.setTitulo(rs.getString(2));
                dto.setDescripcion(rs.getString(3));
                dto.setFecha(rs.getString(4));
                dto.setLugar(rs.getString(5));
                lista.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void crearEvento(String titulo, String descripcion, String fecha, String lugar) {

        String sql = "INSERT INTO evento (titulo, descripcion, fecha, lugar) VALUES (?,?,?,?)";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, titulo);
            ps.setString(2, descripcion);
            ps.setString(3, fecha);
            ps.setString(4, lugar);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void borrarEvento(int idEvento) {

        String sql = "DELETE FROM evento WHERE id_evento = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idEvento);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
