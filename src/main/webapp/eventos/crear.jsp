<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Crear Evento</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          rel="stylesheet" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <jsp:include page="../navbar.jsp"/>

    <%
        String error = (String) session.getAttribute("errorMsg");
        if (error != null) {
    %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
    <%
            session.removeAttribute("errorMsg");
        }
    %>
    <h2 class="my-3">Crear nuevo evento</h2>

    <form method="post" action="<%= request.getContextPath() %>/eventos" class="mb-4">
        <input type="hidden" name="action" value="guardar"/>

        <div class="mb-3">
            <label class="form-label">Título</label>
            <input type="text" class="form-control" name="titulo" required
                   value="<%= request.getAttribute("titulo") != null ? request.getAttribute("titulo") : "" %>"/>
        </div>

        <div class="mb-3">
            <label class="form-label">Descripción</label>
            <input type="text" class="form-control" name="descripcion"
                   value="<%= request.getAttribute("descripcion") != null ? request.getAttribute("descripcion") : "" %>"/>
        </div>

        <div class="row">
            <div class="col-md-6 mb-3">
                <label class="form-label">Fecha (YYYY-MM-DD)</label>
                <input type="date" class="form-control" name="fecha" required
                       value="<%= request.getAttribute("fecha") != null ? request.getAttribute("fecha") : "" %>"/>
            </div>
            <div class="col-md-6 mb-3">
                <label class="form-label">Lugar</label>
                <input type="text" class="form-control" name="lugar"
                       value="<%= request.getAttribute("lugar") != null ? request.getAttribute("lugar") : "" %>"/>
            </div>
        </div>

        <button type="submit" class="btn btn-success">Crear Evento</button>
        <a href="<%= request.getContextPath() %>/eventos?action=lista" class="btn btn-secondary">Cancelar</a>
    </form>
</div>
</body>
</html>
