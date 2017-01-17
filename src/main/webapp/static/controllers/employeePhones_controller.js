
'use strict'
var app = angular.module('app');

app.controller('EmployeePhonesCtrl',  function ($scope,
		                                  $http,
		                                  $log,
		                                  $timeout, 
		                                  EmployeeService, 
		                                  uiGridConstants,
		                                  formlyVersion,
		                                  $uibModal,
		                                  CONST,
		                                  shareAccion,
		                                  $rootScope,
		                                  $location,
		                                  ListaTelefonos
		                                  ) {
	
	var vm = this;
	$scope.newPhone = newPhone;
	
	$scope.gridOptions = {
		enableColumnResizing: true,
	    enableRowSelection: true,
	    enableSelectAll: false,
	    selectionRowHeaderWidth: 40,
	    rowHeight: 40,
	    enableRowHeaderSelection:false,
    };
   
  $scope.gridOptions.columnDefs = [
    {name: 'id'},
    {field: 'contactnumber', displayName: 'Number', required: true},        
    {field: 'phonetype', displayName:'Tipo', required: true}
  ];
  
  function fetchEmployeePhones (employee){
      EmployeeService.fetchEmployeePhones(employee.id)
          .then(
          function(d) {
        	  employee.phoneNumbers = d;
        	  $scope.gridOptions.data = d;
          },
          function(errResponse){
        	  employee.phoneNumbers = null;
              console.error('Error en acceso lista de telefonos de un empleado');
          }
      );
  }
  $scope.$on(CONST.EVENTO, function(event, data) {
      if (data === 'refreshGrid'){
         fetchEmployeePhones(ListaTelefonos.getObject());
      }
});
  
  $log.log ("Entrando en employeePhonesCtrl" );
  
  $scope.gridOptions.multiSelect = false;
  
  fetchEmployeePhones(ListaTelefonos.getObject());
  
 
  $scope.gridOptions.onRegisterApi = function(gridApi){
      $scope.gridApi = gridApi;
      gridApi.selection.on.rowSelectionChanged($scope,function(row){
     	editPhone (row.entity);  
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

  function editPhone(phone){
      var msg = 'row selected ' + phone + ':' + JSON.stringify(phone, null,2);
      $log.log(msg);
      shareAccion.setAccion('Update');
      edit(phone);
  }
  
  function newPhone () {
    	shareAccion.setAccion('Add');
    	edit ({});
  };
  
  function edit(model) {
      	
        var result = $uibModal.open({
        templateUrl: 'static/templates/employeeModalCRUD.html',
        controller: 'EmployeePhoneModalButtomCtrl',
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
  
    var phoneTypes = [
    	{name : "MOVIL DE TRABAJO", value: "MOVIL TRABAJO"},
    	{name : "MOVIL PERSONAL", value: "MOVIL PERSONAL"},
    	{name : "FIJO DE CASA", value: "FIJO CASA"}
    ];

    // helper functions
    function getFormFields() {
      return [
        {key:'contactnumber',
         type:'input',
         templateOptions:{
        	label:'Numero',
        	placeholder:'Numero',
        	required : true
          }
        },
        {key:'phonetype',
         type: "select",
         templateOptions:{           	
            type: "text",
           	label:'Tipo',
           	required : true,
           	options: phoneTypes
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
    