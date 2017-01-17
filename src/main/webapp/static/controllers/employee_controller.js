
'use strict'
var app = angular.module('app');

// Para compartir la acción con el controlador modal con el fin de establecer 
// los botones pertinentes según la acción del operador (alta o modificaciones/bajas) 
var fact =app.factory('shareAccion', function() {
    var accion = {name:''};
    return {
       getAccion : function (){
    	             return accion.name;
                   },
       setAccion : function (acc){
    	             accion.name = acc;
       }
    };
 });

// Lista de telefonos de un empleados. Para consulta / modificacion telefonos de un empleado
var listaTelefonos = app.factory('ListaTelefonos', function(){
	  var messages = {};
	  messages.object; // objeto empleado
	  messages.list;   // copia de telefonos asociado a gridOptions.data
	  
	  return {
		  
	      getObject (){
	    	  return messages.object;
	      },
	      
	      setObject (objeto){
	    	  messages.object = objeto;
	      },
	      
	      copyToObject (fuente){
	    	 messages.object =JSON.parse(JSON.stringify(fuente));  
	      },
	      
		  getList: function(){
	         return messages.list;	            
	      },
	      
	      setList: function (lista){
	    	  messages.list = lista;
	      },
	      
	      copyToList: function (fuente){
	    	  messages.list = JSON.parse(JSON.stringify(fuente));
	      }
  	};
 });

// Para trabajar con campos del formulario tipo datepicker
app.run(function(formlyConfig) {
	  var attributes = [
	    'date-disabled',
	    'custom-class',
	    'show-weeks',
	    'starting-day',
	    'init-date',
	    'min-mode',
	    'max-mode',
	    'format-day',
	    'format-month',
	    'format-year',
	    'format-day-header',
	    'format-day-title',
	    'format-month-title',
	    'year-range',
	    'shortcut-propagation',
	    'datepicker-popup',
	    'show-button-bar',
	    'current-text',
	    'clear-text',
	    'close-text',
	    'close-on-date-selection',
	    'datepicker-append-to-body'
	  ];

	  var bindings = [
	    'datepicker-mode',
	    'min-date',
	    'max-date'
	  ];
	  
	  var ngModelAttrs = {};

	  angular.forEach(attributes, function(attr) {
	    ngModelAttrs[camelize(attr)] = {attribute: attr};
	  });

	  angular.forEach(bindings, function(binding) {
	    ngModelAttrs[camelize(binding)] = {bound: binding};
	  });

	  console.log(ngModelAttrs);
	  
	  formlyConfig.setType({
	    name: 'datepicker',
	    templateUrl:  'static/templates/datepicker.html',
	    wrapper: ['bootstrapLabel', 'bootstrapHasError'],
	    defaultOptions: {
	      ngModelAttrs: ngModelAttrs,
	      templateOptions: {
	        datepickerOptions: {
	          format: 'yyyy-MM-dd',
	          initDate: new Date()
	        }
	      }
	    },
	    controller: ['$scope', function ($scope) {
	      $scope.datepicker = {};

	      $scope.datepicker.opened = false;

	      $scope.datepicker.open = function ($event) {
	        $scope.datepicker.opened = !$scope.datepicker.opened;
	      };
	    }]
	  });
	  
	  function camelize(string) {
		    string = string.replace(/[\-_\s]+(.)?/g, function(match, chr) {
		      return chr ? chr.toUpperCase() : '';
		    });
		    // Ensure 1st char is always lowercase
		    return string.replace(/^([A-Z])/, function(match, chr) {
		      return chr ? chr.toLowerCase() : '';
		    });
		  }
		});
// fin para definir campos de tipo datepicker


app.controller('EmployeeCtrl',  function ($scope,
		                                  $http,
		                                  $log,
		                                  $timeout, 
		                                  EmployeeService, 
		                                  uiGridConstants,
		                                  formlyVersion,
		                                  $uibModal,
		                                  CONST,
		                                  shareAccion,
		                                  $location,
		                                  ListaTelefonos
		                                  ) {
	
	var vm = this;
	$scope.newEmployee = newEmployee;
	
	$scope.gridOptions = {
		enableColumnResizing: true,
	    enableRowSelection: true,
	    enableSelectAll: false,
	    selectionRowHeaderWidth: 40,
	    rowHeight: 40,
	    enableRowHeaderSelection:false,
    };
   
  $scope.gridOptions.columnDefs = [
    { name: 'id',

    },

    { name: 'phoneNumbers',   
      cellTemplate:'<button class="btn btn-default" ng-click="grid.appScope.editPhones(row.entity);$event.stopPropagation();"><div uib-tooltip="edit" tooltip-placement="right"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span></div></button>',
      enableSorting: false,
      enableHiding: false,
      enableColumnMenu: false,
      displayName: 'Phones'}, 
    
    
    { name: 'name'},
    { name: 'joiningDate',  type:'date', displayName: "Cust Date", cellFilter: 'date:"dd-MM-yyyy"', required: true},
    { name: 'salary', displayName: 'salary (not focusable)', allowCellFocus : false },
    { name: 'ssn', required: true }
  ];
 
  $scope.gridOptions.multiSelect = false;
  
  fetchAllEmployees();
  
  function fetchAllEmployees(){
      EmployeeService.fetchAllEmployees()
          .then(
          function(d) {
        	  $scope.gridOptions.data = d;
          },
          function(errResponse){
              console.error('Error en acceso lista empleados');
          }
      );
  }
 

  
  $scope.$on(CONST.EVENTO, function(event, data) {
		       if (data === 'refreshGrid'){
		    	   fetchAllEmployees();
		       }
  });
 
  
  $scope.gridOptions.onRegisterApi = function(gridApi){
      $scope.gridApi = gridApi;
      gridApi.selection.on.rowSelectionChanged($scope,function(row){
    	editEmployee (row.entity);  
      });

      gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
        var msg = 'rows changed ' + rows.length;
        $log.log(msg);
      });
    };
    

  vm.edit = edit;

  vm.author = getAuthor();
  vm.exampleTitle = 'AngularUI Bootstrap Modal';
  vm.env = getENV();

    
  function editEmployee(employee){
      var msg = 'row selected ' + employee+ ':' + JSON.stringify(employee, null,2);
      $log.log(msg);
      shareAccion.setAccion('Update');
      var date = new Date (employee.joiningDate);
      employee.joiningDate = date;
      edit(employee);
	  
  }
  
  $scope.editPhones = function (employee){
	  $log.log ("editPhones " +  JSON.stringify(employee, null,2));
      // Las lista de telefono está en LAZY en la tabla de empleados,  por lo que la lista
	  // de tedlefonos employee.phoneNumbers está vacia y se consulta en este punto.
	  
	  //fetchEmployeePhones (employee);
	  ListaTelefonos.setObject(employee);

	  $location.path ("/Gestion_Telefonos");	  
  };
  
  function newEmployee () {
	    $log.log("newEmployee");
    	shareAccion.setAccion('Add');
    	edit ({});
  };
  
  function edit(model) {
      	
      var result = $uibModal.open({
        templateUrl: 'static/templates/employeeModalCRUD.html',
        controller: 'EmployeeModalButtomCtrl',
        controllerAs: 'vm',
        resolve: {
          formData: function() {
            return {
              fields: getFormFields(),
              model: model
            }
          }
        }
      }).result;
      
//      if (add) {
//        result.then(function(model) {
//          vm.history.push(model);
//        });
//      }
    }

    // helper functions
    function getFormFields() {
      return [
        {key:'name',
         type:'input',
         templateOptions:{
        	  'label':'Name',
        	  'placeholder':'Name'
          }
        },
        {   key:'joiningDate',
        	type: "datepicker",
            templateOptions:{           	
            type: "text",
            datepickerPopup: "yyyy-MM-dd",
           	label:'Date',
           	required : true
            }
         },
         
         {key:'salary',
          type:'input',
          templateOptions:{
            	label:'Salary',
            	placeholder:'Salary'
              }
         },
         {key:'ssn',
          type:'input',
          templateOptions:{
            label:'Ssn',
            placeholder:'Ssn',
            required : true
              }
         }, 
                

      ];
    }

    function getAuthor() {
      return { // optionally fill in your info below :-)
        name: 'aabrook',
        url: 'https://github.com/aabrook'
      };
    }

    function getENV() {
      return {
        angularVersion: angular.version.full,
        formlyVersion: formlyVersion
      };
    }


  });
    