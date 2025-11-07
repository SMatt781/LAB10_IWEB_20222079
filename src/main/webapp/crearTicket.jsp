<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.plantilla.beans.Evento" %>
<jsp:useBean id="listaEventos" type="java.util.ArrayList<com.example.plantilla.beans.Evento>" scope="request" />

<%
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
    <title>Crear Tipo de Ticket</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          rel="stylesheet" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <jsp:include page="navbar.jsp"/>

    <h2 class="my-3">Crear Tipo de Ticket</h2>

    <form method="post" action="<%= request.getContextPath() %>/tickets" class="mb-3">
        <input type="hidden" name="action" value="crear"/>

        <div class="mb-3">
            <label class="form-label">Evento</label>
            <select name="id_evento" class="form-select" required>
                <option value="">-- Seleccione un evento --</option>
                <%
                    String idEventoSel = (String) request.getAttribute("id_evento");
                    for (Evento e : listaEventos) {
                        boolean sel = idEventoSel != null && idEventoSel.equals(String.valueOf(e.getId_evento()));
                %>
                <option value="<%= e.getId_evento() %>" <%= sel ? "selected" : "" %>>
                    <%= e.getTitulo() %> - <%= e.getFecha() %>
                </option>
                <% } %>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Nombre del Ticket</label>
            <input name="nombre" type="text" class="form-control"
                   value="<%= request.getAttribute("nombre") != null ? request.getAttribute("nombre") : "" %>"
                   placeholder="Ej.: General, VIP, etc." required />
        </div>

        <div class="row">
            <div class="col-md-4 mb-3">
                <label class="form-label">Precio (S/.)</label>
                <input name="precio" type="number" step="0.01" min="0" class="form-control"
                       value="<%= request.getAttribute("precio") != null ? request.getAttribute("precio") : "" %>"
                       placeholder="0.00" required />
            </div>
            <div class="col-md-4 mb-3">
                <label class="form-label">Cupo total</label>
                <input name="cupoTotal" type="number" min="0" class="form-control"
                       value="<%= request.getAttribute("cupoTotal") != null ? request.getAttribute("cupoTotal") : "" %>"
                       placeholder="0" required />
            </div>
            <div class="col-md-4 mb-3">
                <label class="form-label">Cupo disponible</label>
                <input name="cupoDisponible" type="number" min="0" class="form-control"
                       value="<%= request.getAttribute("cupoDisponible") != null ? request.getAttribute("cupoDisponible") : "" %>"
                       placeholder="0" required />
            </div>
        </div>

        <button type="submit" class="btn btn-success">Crear</button>
        <a href="<%= request.getContextPath() %>/tickets" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>
