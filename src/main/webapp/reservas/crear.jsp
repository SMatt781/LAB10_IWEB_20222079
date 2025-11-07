<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.plantilla.beans.Usuario" %>
<%@ page import="com.example.plantilla.beans.TicketTipo" %>
<%@ page import="com.example.plantilla.beans.Evento" %>
<%@ page import="java.util.ArrayList" %>

<jsp:useBean id="listaUsuarios" type="java.util.ArrayList<com.example.plantilla.beans.Usuario>" scope="request"/>
<jsp:useBean id="listaEventos" type="java.util.ArrayList<com.example.plantilla.beans.Evento>" scope="request"/>
<jsp:useBean id="listaTiposTicket" type="java.util.ArrayList<com.example.plantilla.beans.TicketTipo>" scope="request"/>

<%
    Integer selectedEventoId = (Integer) request.getAttribute("selectedEventoId");
    String error = (String) session.getAttribute("errorMsg");
    if (error != null) {
%>
<div class="alert alert-danger" role="alert"><%= error %></div>
<%
        session.removeAttribute("errorMsg");
    }
%>

<html>
<head>
    <title>Crear Reserva</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          rel="stylesheet" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <jsp:include page="../navbar.jsp"/>

    <h2 class="my-3">Crear Nueva Reserva</h2>

    <form method="get" action="<%= request.getContextPath() %>/reservas" class="mb-3">
        <input type="hidden" name="action" value="crear"/>
        <div class="mb-3">
            <label class="form-label">Evento</label>
            <select name="idEvento" class="form-select" onchange="this.form.submit()">
                <option value="">-- Seleccione un evento --</option>
                <% for (Evento ev : listaEventos) { %>
                <option value="<%= ev.getId_evento() %>"
                        <%= (selectedEventoId != null && selectedEventoId == ev.getId_evento()) ? "selected" : "" %>>
                    <%= ev.getTitulo() %>
                </option>
                <% } %>
            </select>
        </div>
    </form>

    <form method="post" action="<%= request.getContextPath() %>/reservas">
        <input type="hidden" name="action" value="guardar"/>
        <input type="hidden" name="idEvento" value="<%= selectedEventoId != null ? selectedEventoId : "" %>"/>

        <div class="mb-3">
            <label class="form-label">Usuario</label>
            <select name="idUsuario" class="form-select" required>
                <option value="">-- Seleccione un usuario --</option>
                <% for (Usuario u : listaUsuarios) { %>
                <option value="<%= u.getId_usuario() %>">
                    <%= u.getNombres() + " " + u.getApellidos() %>
                </option>
                <% } %>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Tipo de Ticket</label>
            <select name="idTicketTipo" class="form-select" <%= (selectedEventoId == null ? "disabled" : "") %> required>
                <% if (selectedEventoId == null || listaTiposTicket.isEmpty()) { %>
                <option value="">Seleccione primero un evento</option>
                <% } else {

                    for (TicketTipo tt : listaTiposTicket) { %>
                <option value="<%= tt.getId_ticket_tipo() %>">
                    <%= tt.getNombre() %> — S/. <%= String.format("%.2f", tt.getPrecio()) %>
                </option>
                <%   }
                } %>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Cantidad</label>
            <input type="number" name="cantidad" class="form-control" value="1" min="1" required/>
        </div>

        <button type="submit" class="btn btn-success" <%= (selectedEventoId == null ? "disabled" : "") %>>
            Guardar Reserva
        </button>
        <a href="<%= request.getContextPath() %>/reservas" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>
