<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="com.example.plantilla.dtos.TicketListadoDTO" %>

<%@ page import="java.util.ArrayList" %>

<jsp:useBean id="listaTickets" type="java.util.ArrayList<com.example.plantilla.dtos.TicketListadoDTO>" scope="request" />

<html>
<head>
    <title>Gestión de Tickets</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          crossorigin="anonymous">
</head>
<body>
<div class="container">
    <jsp:include page="navbar.jsp"/>

    <h2 class="my-3">Listado de Tickets</h2>
    <a href="<%= request.getContextPath() %>/tickets?action=crear" class="btn btn-primary mb-3">
        Añadir Ticket
    </a>

    <%
        String error = (String) session.getAttribute("errorMsg");
        if (error != null) {
    %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
    <%
            session.removeAttribute("errorMsg");
        }
    %>

    <%
        String success = (String) session.getAttribute("successMsg");
        if (success != null) {
    %>
    <div class="alert alert-success" role="alert">
        <%= success %>
    </div>
    <%
            session.removeAttribute("successMsg");
        }
    %>
    <table class="table">
        <thead>
        <tr>
            <th>Evento</th>
            <th>Lugar</th>
            <th>Fecha</th>
            <th>Ticket</th>
            <th>Precio</th>
            <th>Cupo Disp.</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <% for (TicketListadoDTO ticket : listaTickets) { %>
        <tr>
            <td><%= ticket.getTituloEvento() %></td>
            <td><%= ticket.getLugarEvento() %></td>
            <td><%= ticket.getFechaEvento() %></td>
            <td><%= ticket.getNombreTicket() %></td>
            <td><%= ticket.getPrecio() %></td>
            <td><%= ticket.getCupoDisponible() %></td>
            <td>
                <a href="<%= request.getContextPath() %>/tickets?action=borrar&id=<%= ticket.getIdTicketTipo() %>"
                   class="btn btn-danger">Borrar</a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>