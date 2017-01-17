
'use strict'
var app = angular.module('app');

/*app.service('getTotalRowsService', ['$http', function ($http){
	
	function getTotalRows () {
		return  $http({
          method: 'GET',
          url: 'http://localhost:8080/SpringRESTHibernateAnotationAngularjs/movimientocount/'
        });
	}	
    return {getTotalRows:getTotalRows};
    	
}]);

app.service('loadPageService',['getTotalRowsService', '$http', function (getTotalRowsService, $http) {

	function getMovimientos (pageNumber,size, columnSort, sentido) {
        return  $http({
          method: 'GET',
          url: 'http://localhost:8080/SpringRESTHibernateAnotationAngularjs/movimientopage/' + '?page='+pageNumber+'&size='+size
               +'&columnSort='+columnSort+'&sentido='+sentido
        });
    }
	
    return {
    	getMovimientos:getMovimientos
    };
	
}]);*/

app.controller('MovimientoCtrl',  function ($scope, $http, $log, MovimientoService,
		                               EmployeeService, uiGridConstants) {
	
	var vm = this;
	
	var paginationOptions = {
			     pageNumber: 1,
				 pageSize: 10,
				 columnSort: 'id',
				 sort: 'asc'
			   };	
	
     $scope.gridOptions = {
			paginationPageSizes: [10, 15, 20, 5],
			paginationPageSize: paginationOptions.pageSize,
			enableColumnMenus:false,
			useExternalPagination: true,

			enableColumnResizing: true,
			enableRowSelection: true,
			enableSelectAll: false,
			selectionRowHeaderWidth: 35,
			rowHeight: 35,
			enableRowHeaderSelection:false,
    };
   
  $scope.gridOptions.columnDefs = [
    { name: 'id' },
    { name: 'date',  type:'date', displayName: "Mov. Date", cellFilter: 'date:"dd-MM-yyyy"'},
    { name: 'accion', displayName: 'Movimiento', allowCellFocus : false },
    { name: 'employeeId', displayName: 'employeeId', allowCellFocus : false },
    { name: 'employeeName', displayName: 'Name', allowCellFocus : false },
    { name: 'employeeJoiningDate',  type:'date', displayName: "Joining Date", cellFilter: 'date:"dd-MM-yyyy"'},
    { name: 'employeeSalary', displayName: 'salary', allowCellFocus : false },
    { name: 'employeeSsn', displayNmae: 'Ssn', allowCellFocus : false}
  ];
  
/*  function solicitarPagina(pagina){
	  pagina = pagina > 0?pagina - 1:0;
	  if (pagina == 0) {
		  getTotalRowsService.getTotalRows()
		  .success(function(data){
			  $scope.gridOptions.totalItems = data
		  })
			  
	  };
	  loadPageService.getMovimientos(pagina, paginationOptions.pageSize,
                                     paginationOptions.columnSort, paginationOptions.sort
		).success(function(data) {
			 $scope.gridOptions.data = data.content;
		});	  	  
  }*/
  
  function solicitarPagina(pagina){
	  pagina = pagina > 0?pagina - 1:0;
	  if (pagina == 0) {
		  MovimientoService.getTotalRows()
		    .then (
		    	function(d){
		    		$scope.gridOptions.totalItems = d
		    	},
		    	function (errResponse){
		    		console.error ('Error en consulta n. pag. mov');
		    	}
           );
      };
 
	  MovimientoService.getMovimientos(pagina, paginationOptions.pageSize,
			  paginationOptions.columnSort, paginationOptions.sort)
	    .then(
	    	function(d){
	    		$scope.gridOptions.data= d.content;
	    	},
	    	function (errResponse){
	    		console.error('Error fetching Movimientos');
	    	}
	    	 );
  };
  
  
 
  $scope.gridOptions.multiSelect = false;
  
  solicitarPagina($scope.gridOptions.pageNumber);  
    
  function fetchAllMovimientos(){
      EmployeeService.fetchAllMovimientos()
          .then(
          function(d) {
        	  $scope.gridOptions.data = d;
          },
          function(errResponse){
              console.error('Error while fetching Movimientos');
          }
      );
  }
   
  $scope.gridOptions.onRegisterApi = function(gridApi){
      //set gridApi on scope
      $scope.gridApi = gridApi;
      gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
 
          if (pageSize != paginationOptions.pageSize){
        	  paginationOptions.pageNumber = 1;
        	  gridApi.pagination.seek(1);
          } else {       
             paginationOptions.pageNumber = newPage;
          }
          paginationOptions.pageSize = pageSize;
          solicitarPagina(paginationOptions.pageNumber);

      });
          
      gridApi.core.on.sortChanged( $scope, function(grid, sortColumns){

          // sortColumns is an array containing just the column sorted in the grid
          // the direction of the first column sorted: "desc" or "asc"
          // en algunas ocasiones sortColumns[0].name es undefined y casca
        if (sortColumns.length > 0){      	  
    	   var name = sortColumns[0].name;
    	   if (name != paginationOptions.columnSort) {   	 
    		   gridApi.pagination.seek(1);
    		   paginationOptions.pageNumber = 1;
    		   grid.pagin
    		   paginationOptions.columnSort = name;
    		   solicitarPagina(paginationOptions.pageNumber)        	  
           };
          
        var sort = sortColumns[0].sort.direction
          
        if (sort != paginationOptions.sort){
        	gridApi.pagination.seek(1);
        	paginationOptions.pageNumber = 1;
        	paginationOptions.sort = sort;
        	solicitarPagina(paginationOptions.pageNumber);
        };
      };       
          // Your logic to do the server sorting
      });
        
      gridApi.selection.on.rowSelectionChanged($scope,function(row){
        var msg = 'row selected ' + row.isSelected + ':' + JSON.stringify(row.entity, null,2);
        $log.log(msg);
      });
      
      gridApi.selection.on
 
      gridApi.selection.on.rowSelectionChangedBatch($scope,function(rows){
        var msg = 'rows changed ' + rows.length;
        $log.log(msg);
      });
    }

});

    

