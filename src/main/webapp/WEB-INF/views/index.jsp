<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-touch.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.js"></script>
   
    <!-- apiCheck is used by formly to validate its api -->
    <script src="http://npmcdn.com/api-check@latest/dist/api-check.js"></script>
    <!-- Angular-UI Bootstrap has tabs directive we want -->
    <script src="http://npmcdn.com/angular-ui-bootstrap@latest/dist/ui-bootstrap-tpls.js"></script>
    <!-- This is the latest version of formly core. -->
    <script src="http://npmcdn.com/angular-formly@latest/dist/formly.js"></script> 
    <!-- This is the latest version of formly bootstrap templates -->
    <script src="http://npmcdn.com/angular-formly-templates-bootstrap@latest/dist/angular-formly-templates-bootstrap.js"></script>
    
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.css" rel="stylesheet">
    

    <!-- ui-select files -->
    <script src="https://cdn.rawgit.com/angular-ui/ui-select/v0.16.0/dist/select.js"></script>
    <link rel="stylesheet" href="https://cdn.rawgit.com/angular-ui/ui-select/v0.16.0/dist/select.css">
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.6/angular-sanitize.js"></script>
    
 
<!--     <script src="http://ui-grid.info/docs/grunt-scripts/csv.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/pdfmake.js"></script>
    <script src="http://ui-grid.info/docs/grunt-scripts/vfs_fonts.js"></script>   
    <script src="http://ui-grid.info/release/ui-grid.js"></script>
    <link rel="stylesheet" href="http://ui-grid.info/release/ui-grid.css" type="text/css"> -->

       

    <script src="<c:url value='/static/lib/ui-grid.js' />"></script>
    
    <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="static/lib/ui-grid.css" type="text/css">    
    <link rel="stylesheet" href="static/css/main.css" type="text/css">
    
    <script src="<c:url value='/static/app.js' />"></script>
    <script src="<c:url value='/static/controllers/employeePhoneModalButtom_controller.js' />"></script>
    <script src="<c:url value='/static/controllers/employeeModalButtom_controller.js' />"></script>
    <script src="<c:url value='/static/controllers/employeePhones_controller.js' />"></script>
    <script src="<c:url value='/static/controllers/employee_controller.js' />"></script>
    <script src="<c:url value='/static/controllers/movimiento_controller.js' />"></script>
    <script src="<c:url value='/static/services/employee_service.js' />"></script>
    <script src="<c:url value='/static/services/movimiento_service.js' />"></script>
    <script src="<c:url value='/static/services/phone_service.js' />"></script>

</head>
<body  ng-app="app">
  
<div class="container">

<header>
   <h1>Angularjs</h1>
</header>

  <nav>
  <ul>
    <li><a href="#Gestion_Empleado">Gestion Empleados</a></li>
    <li><a href="#Consulta_movimientos">Listado Movimientos</a></li>
    <li><a href="#">Otros</a></li>
  </ul>
  
 
  </nav>
  
  <article>
    <ng-view></ng-view>

  </article> 

  
  <footer>Antonio</footer> 
  
  </div>
  
  </body>
</html>