package com.example.plantilla.daos;

import com.example.plantilla.beans.Usuario;
import com.example.plantilla.dtos.ReservaListadoDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UsuarioDao extends DaoBase{

    public ArrayList<Usuario> listar() {

        ArrayList<Usuario> lista = new ArrayList<>();


        String sql = "SELECT u.id_usuario, u.nombres, u.apellidos, u.email, u.creado_en, u.actualizado_en " +
                "FROM usuario u ";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId_usuario(rs.getInt("id_usuario"));
                u.setNombres(rs.getString("nombres"));
                u.setApellidos(rs.getString("apellidos"));
                u.setEmail(rs.getString("email"));
                u.setCreado_en(rs.getString("creado_en"));
                u.setActualizado_en(rs.getString("actualizado_en"));
                lista.add(u);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
