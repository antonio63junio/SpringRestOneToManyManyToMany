/**
 * 
 */
var app = angular.module('app', ['ngTouch', 'ui.grid', 'ui.grid.pagination',
                       'ui.grid.selection','ui.grid.resizeColumns', 'ngRoute',
                       'formly', 'formlyBootstrap', 'ui.bootstrap','ui.bootstrap.tpls']);

app.config(function($routeProvider) {
	   $routeProvider
	    .when("/", {
	        template : "<h1>AngularJS</h1><p> CRUD con angularjs, ui-grid, formly, datepicker, spring, hibernate, REST</p>"
	    })
	    .when("/Gestion_Empleado", {
	        templateUrl : "static/templates/employee.html",
	        controller : "EmployeeCtrl"
	    })
	    .when("/Consulta_movimientos", {
	        templateUrl : "static/templates/movimientos.html",
	        controller :"MovimientoCtrl"	
	    })
	    .when("/Gestion_Telefonos", {
	        templateUrl : "static/templates/employeePhones.html",
	        controller :"EmployeePhonesCtrl"	
	    })
	    .otherwise({
	        template : "<h1>None</h1><p>Nothing has been selected,</p>"
	    });
	}

);

app.constant('CONST', {
		'EVENTO': 'evento',
		'REST_SERVICE_URI_BASE' : 'http://localhost:8080/SpringRestOneToManyManyToOne4'
		
});