package com.example.plantilla.servlets;

import com.example.plantilla.beans.Evento;
import com.example.plantilla.daos.EventoDao;
import com.example.plantilla.dtos.EventoListadoDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

@WebServlet(name = "EventoServlet", value = "/eventos")
public class EventoServlet extends HttpServlet {

    EventoDao eventoDao = new EventoDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        switch (action) {
            case "lista":
                ArrayList<EventoListadoDTO> lista = eventoDao.listarEventosDTO();
                request.setAttribute("listaEventos", lista);

                RequestDispatcher rd = request.getRequestDispatcher("eventos/lista.jsp");
                rd.forward(request, response);
                break;

            case "crear": {
                request.getRequestDispatcher("eventos/crear.jsp").forward(request, response);
                break;
            }

            case "borrar":
                try {
                    int idEvento = Integer.parseInt(request.getParameter("id"));
                    eventoDao.borrarEvento(idEvento);
                } catch (NumberFormatException e) {
                    System.out.println("Error al parsear ID para borrar evento");
                }
                response.sendRedirect(request.getContextPath() + "/eventos");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        if ("guardar".equals(action)) {


            String titulo = request.getParameter("titulo");
            String descripcion = request.getParameter("descripcion");
            String fecha = request.getParameter("fecha");
            String lugar = request.getParameter("lugar");

            request.setAttribute("titulo", titulo);
            request.setAttribute("descripcion", descripcion);
            request.setAttribute("fecha", fecha);
            request.setAttribute("lugar", lugar);

            String errorMsg = null;

            if (titulo.isEmpty()) {
                errorMsg = "El título es obligatorio.";
            } else if (lugar.isEmpty()) {
                errorMsg = "El lugar es obligatorio.";
            } else if (fecha.isEmpty()) {
                errorMsg = "La fecha es obligatoria.";
            } else {
                try {
                    LocalDate fechaEvento = LocalDate.parse(fecha);
                    LocalDate hoy = LocalDate.now();
                    if (!fechaEvento.isAfter(hoy)) {
                        errorMsg = "Error: La fecha del evento debe ser futura.";
                    }
                } catch (DateTimeParseException e) {
                    errorMsg = "Error: El formato de la fecha es incorrecto.";
                }
            }
            if (errorMsg != null) {
                session.setAttribute("errorMsg", errorMsg);
                request.getRequestDispatcher("eventos/crear.jsp").forward(request, response);
                return;
            }
            eventoDao.crearEvento(titulo, descripcion, fecha, lugar);
            session.setAttribute("successMsg", "Evento creado exitosamente.");
            response.sendRedirect(request.getContextPath() + "/eventos?action=lista");
            return;
        }
        response.sendRedirect(request.getContextPath() + "/eventos?action=lista");

    }
}