package com.example.plantilla.servlets;

import com.example.plantilla.daos.EventoDao;
import com.example.plantilla.daos.TicketDao;
import com.example.plantilla.dtos.TicketListadoDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "TicketServlet", value = "/tickets")
public class TicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        TicketDao ticketDao = new TicketDao();
        EventoDao eventoDao = new EventoDao();


        switch (action) {
            case "lista":
                ArrayList<TicketListadoDTO> lista = ticketDao.listarTickets();
                request.setAttribute("listaTickets", lista);


                request.setAttribute("listaEventos", eventoDao.listar());

                request.getRequestDispatcher("ticketsLista.jsp").forward(request, response);
                break;

            case "crear": {
                request.setAttribute("listaEventos", eventoDao.listar());

                request.setAttribute("id_evento", request.getParameter("id_evento"));
                request.setAttribute("nombre", request.getParameter("nombre"));
                request.setAttribute("precio", request.getParameter("precio"));
                request.setAttribute("cupoTotal", request.getParameter("cupoTotal"));
                request.setAttribute("cupoDisponible", request.getParameter("cupoDisponible"));

                request.getRequestDispatcher("crearTicket.jsp").forward(request, response);
                break;
            }

            case "borrar":
                int idBorrar = Integer.parseInt(request.getParameter("id"));
                ticketDao.borrarTicket(idBorrar);
                response.sendRedirect(request.getContextPath() + "/tickets");
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        TicketDao ticketDao = new TicketDao();
        EventoDao eventoDao = new EventoDao();

        HttpSession session = request.getSession();

        if ("crear".equals(action)) {
            String idEventoStr = request.getParameter("id_evento");
            String nombre      = request.getParameter("nombre");
            String precioStr   = request.getParameter("precio");
            String cupoTotStr  = request.getParameter("cupoTotal");
            String cupoDispStr = request.getParameter("cupoDisponible");

            request.setAttribute("id_evento", idEventoStr);
            request.setAttribute("nombre", nombre);
            request.setAttribute("precio", precioStr);
            request.setAttribute("cupoTotal", cupoTotStr);
            request.setAttribute("cupoDisponible", cupoDispStr);

            try{
                int idEvento   = Integer.parseInt(idEventoStr);
                double precio  = Double.parseDouble(precioStr);
                int cupoTotal  = Integer.parseInt(cupoTotStr);
                int cupoDisp   = Integer.parseInt(cupoDispStr);

                String errorMsg = null;

                if (nombre == null || nombre.trim().isEmpty()) {
                    errorMsg = "El nombre del ticket es obligatorio.";
                } else if (precio < 0) {
                    errorMsg = "El precio no puede ser negativo.";
                } else if (cupoTotal < 0) {
                    errorMsg = "El cupo total no puede ser negativo.";
                } else if (cupoDisp < 0) {
                    errorMsg = "El cupo disponible no puede ser negativo.";
                } else if (cupoDisp > cupoTotal) {
                    errorMsg = "El cupo disponible no puede ser mayor al cupo total.";
                }


                if (errorMsg != null) {
                    session.setAttribute("errorMsg", errorMsg);

                    request.setAttribute("listaEventos", eventoDao.listar());
                    request.getRequestDispatcher("crearTicket.jsp").forward(request, response);
                    return;
                }

                ticketDao.crearTicket(idEvento, nombre.trim(), precio, cupoTotal, cupoDisp);
                session.setAttribute("successMsg", "Ticket creado exitosamente.");
                response.sendRedirect(request.getContextPath() + "/tickets?action=lista");
                return;
            } catch (NumberFormatException e) {
                session.setAttribute("errorMsg", "Error de formato: verifique id_evento, precio y cupos.");

                request.setAttribute("listaEventos", eventoDao.listar());
                request.getRequestDispatcher("crearTicket.jsp").forward(request, response);
                return;
            }
        }
        response.sendRedirect(request.getContextPath() + "/tickets");
    }
}
