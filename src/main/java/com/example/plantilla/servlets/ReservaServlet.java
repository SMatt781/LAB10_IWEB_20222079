package com.example.plantilla.servlets;

import com.example.plantilla.beans.Evento;
import com.example.plantilla.beans.TicketTipo;
import com.example.plantilla.daos.EventoDao;
import com.example.plantilla.daos.ReservaDao;
import com.example.plantilla.daos.TicketDao;
import com.example.plantilla.daos.UsuarioDao;
import com.example.plantilla.dtos.ReservaListadoDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(name = "ReservaServlet", value = "/reservas")
public class ReservaServlet extends HttpServlet {

    ReservaDao reservaDao = new ReservaDao();
    UsuarioDao usuarioDao = new UsuarioDao();
    TicketDao ticketDao = new TicketDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        switch (action) {
            case "lista":
                ArrayList<ReservaListadoDTO> lista = reservaDao.listarReservas();
                request.setAttribute("listaReservas", lista);

                RequestDispatcher rd = request.getRequestDispatcher("reservas/lista.jsp");
                rd.forward(request, response);
                break;

            case "crear":
                EventoDao eventoDao = new EventoDao();

                request.setAttribute("listaUsuarios", usuarioDao.listar());
                request.setAttribute("listaEventos", eventoDao.listar());

                String idEvento = request.getParameter("idEvento");
                ArrayList<TicketTipo> tickets = new ArrayList<>();
                Integer selectedEventoId = null;

                if (idEvento != null && !idEvento.isEmpty()) {
                    try {
                        selectedEventoId = Integer.parseInt(idEvento);
                        tickets = ticketDao.listarTiposDeTicket(selectedEventoId);
                    } catch (NumberFormatException ignored) {}
                }

                request.setAttribute("listaTiposTicket", tickets);
                request.setAttribute("selectedEventoId", selectedEventoId);

                RequestDispatcher rdCrear = request.getRequestDispatcher("reservas/crear.jsp");
                rdCrear.forward(request, response);
                break;

            case "borrar":
                try {
                    int idItem = Integer.parseInt(request.getParameter("id"));
                    reservaDao.borrarReserva(idItem);
                } catch (NumberFormatException e) {
                    System.out.println("Error al parsear ID para borrar reserva");
                }
                response.sendRedirect(request.getContextPath() + "/reservas");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        ReservaDao reservaDao = new ReservaDao();
        TicketDao ticketDao = new TicketDao();
        EventoDao eventoDao = new EventoDao();

        if ("guardar".equals(action)) {


            try {
                int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
                int idEvento      = Integer.parseInt(request.getParameter("idEvento"));
                int idTicketTipo = Integer.parseInt(request.getParameter("idTicketTipo"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));

                TicketTipo ticket = ticketDao.ObtenerTicketporID(idTicketTipo);
                Evento evento = eventoDao.obtieneEventoporID(ticket.getId_evento());

                if (ticket == null || ticket.getId_evento() != idEvento) {
                    session.setAttribute("errorMsg", "Error: el tipo de ticket no corresponde al evento seleccionado.");
                    response.sendRedirect(request.getContextPath() + "/reservas?action=crear&idEvento=" + idEvento);
                    return;
                }

                if (cantidad > ticket.getCupo_disponible()) {
                    session.setAttribute("errorMsg", "Error: La cantidad (" + cantidad + ") supera el cupo disponible (" + ticket.getCupo_disponible() + ").");
                    response.sendRedirect(request.getContextPath() + "/reservas?action=crear");
                    return;
                }

                LocalDate fechaEvento = LocalDate.parse(evento.getFecha());
                LocalDate hoy = LocalDate.now();

                if (fechaEvento.isBefore(hoy) || fechaEvento.isEqual(hoy)) {
                    session.setAttribute("errorMsg", "Error: No se pueden comprar tickets para un evento que es hoy o que ya pasó.");
                    response.sendRedirect(request.getContextPath() + "/reservas?action=crear");
                    return;
                }


                reservaDao.crearReserva(idUsuario, idTicketTipo, cantidad);

                session.setAttribute("successMsg", "Reserva creada exitosamente.");
                response.sendRedirect(request.getContextPath() + "/reservas?action=lista");

            } catch (NumberFormatException e) {
                session.setAttribute("errorMsg", "Error de formato: la cantidad debe ser un número.");
                response.sendRedirect(request.getContextPath() + "/reservas?action=crear");
                e.printStackTrace();
            }
        }
    }
}