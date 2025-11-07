<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.plantilla.dtos.EventoListadoDTO" %>
<%@ page import="java.util.ArrayList" %>

<jsp:useBean id="listaEventos" type="java.util.ArrayList<com.example.plantilla.dtos.EventoListadoDTO>" scope="request"/>

<html>
<head>
  <title>Gestión de Eventos</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
        rel="stylesheet"
        integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
        crossorigin="anonymous">
</head>
<body>
<div class="container">
    <jsp:include page="/navbar.jsp"/>


  <h2 class="my-3">Eventos</h2>
    <a href="<%= request.getContextPath() %>/eventos?action=crear" class="btn btn-primary mb-3">
        Añadir Evento
    </a>
  <%
    String error = (String) session.getAttribute("errorMsg");
    if (error != null) {
  %>
  <div class="alert alert-danger" role="alert"><%= error %></div>
  <%
      session.removeAttribute("errorMsg");
    }
    String success = (String) session.getAttribute("successMsg");
    if (success != null) {
  %>
  <div class="alert alert-success" role="alert"><%= success %></div>
  <%
      session.removeAttribute("successMsg");
    }
  %>

  <table class="table">
    <thead>
    <tr>
      <th>ID</th>
      <th>Título</th>
      <th>Descripción</th>
      <th>Fecha</th>
      <th>Lugar</th>
      <th></th>
    </tr>
    </thead>
    <tbody>

    <% for (EventoListadoDTO dto : listaEventos) { %>
    <tr>
      <td><%= dto.getId_evento() %></td>
      <td><%= dto.getTitulo() %></td>
      <td><%= dto.getDescripcion() %></td>
      <td><%= dto.getFecha() %></td>
      <td><%= dto.getLugar() %></td>
      <td>
        <a href="<%= request.getContextPath() %>/eventos?action=borrar&id=<%= dto.getId_evento() %>"
           class="btn btn-danger btn-sm">Borrar</a>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
</body>
</html>