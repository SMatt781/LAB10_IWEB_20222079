<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.plantilla.dtos.ReservaListadoDTO" %>
<%@ page import="java.util.ArrayList" %>

<jsp:useBean id="listaReservas" type="java.util.ArrayList<ReservaListadoDTO>" scope="request"/>

<html>
<head>
  <title>Lista de Reservas</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
        integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
        rel="stylesheet" crossorigin="anonymous">
</head>
<body>
<div class="container">
  <jsp:include page="../navbar.jsp"/>

  <h2 class="my-3">Reservas</h2>

  <a href="<%= request.getContextPath() %>/reservas?action=crear" class="btn btn-primary mb-3">
    Añadir Reserva
  </a>

  <table class="table">
    <thead>
    <tr>
      <th>Evento</th>
      <th>Fecha</th>
      <th>Usuario</th>
      <th>Email</th>
      <th>Ticket</th>
      <th>Cantidad</th>
      <th></th>
    </tr>
    </thead>
    <tbody>
    <% for (ReservaListadoDTO dto : listaReservas) { %>
    <tr>
      <td><%= dto.getTituloEvento() %></td>
      <td><%= dto.getFechaEvento() %></td>
      <td><%= dto.getUsuario().getNombres() + " " + dto.getUsuario().getApellidos() %></td>
      <td><%= dto.getUsuario().getEmail() %></td>
      <td><%= dto.getNombreTicket() %></td>
      <td><%= dto.getCantidadReservas() %></td>
      <td>
        <a href="<%= request.getContextPath() %>/reservas?action=borrar&id=<%= dto.getIdItem() %>"
           class="btn btn-danger btn-sm">Borrar</a>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
</div>
</body>
</html>