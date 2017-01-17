  app.controller('EmployeeModalButtomCtrl', function (
		  $scope, 
		  $uibModalInstance, 
		  formData, 
		  $log,
		  EmployeeService, 
		  $rootScope, 
		  CONST, 
		  shareAccion) {
    var vm = this;

    vm.cancel = cancel;
    vm.aplicarAccion = aplicarAccion;

    vm.formData = formData;
    vm.originalFields = angular.copy(vm.formData.fields); 
    
    $scope.$watch(function () {return shareAccion.getAccion();},
    		  function (newAccion, oldAccion){
    		   	  //if (newAccion !== oldAccion ) {
    		   		  establecerBotonesForm()
    		   	  // }
              });

    function establecerBotonesForm(){
    	$log.log("EmployeeModalButtomCtrl model:" + JSON.stringify(vm.formData.model, null,2));
    	switch (shareAccion.getAccion()){
    	case 'Update':
    		$scope.showDeleteButton = true;
    		$scope.showUpdateButton = true;
    		$scope.showAddButton = false;
    		break;
    	case 'Add':
    		$scope.showDeleteButton = false;
    		$scope.showUpdateButton = false;
    		$scope.showAddButton = true;
    		break;
        default :
        	$scope.showDeleteButton =  false;
            $scope.showUpdateButton =  false;
            $scope.showAddButton = false;        	
    		
    	}
    };
    
	    function aplicarAccion (accion) {
	    	switch (accion){
	    	case  'Update':
	    	       EmployeeService.updateEmployee( formData.model, formData.model.id)
	    	       .then( 
	    	       function() {
	    	    	   $rootScope.$broadcast(CONST.EVENTO, 'refreshGrid'); 
	    	    	   $uibModalInstance.close(vm.formData.model);
	    	       },
	    	       function(errResponse){
	    	    	   alert(JSON.stringify(errResponse, null,2));
	    	       }); 	
	                  
	               break;
	    	case 'Add':
		            EmployeeService.createEmployee(formData.model)
		            .then(
		            function() {
		            	$rootScope.$broadcast(CONST.EVENTO, 'refreshGrid');
		            	$uibModalInstance.close(vm.formData.model);
		            },
		            function(errResponse){
		            	alert(JSON.stringify(errResponse, null,2));
		            });
		            
	    	       break;
	    	       
	    	case 'Delete':
		            EmployeeService.deleteEmployee(formData.model)
		            .then(
		            function() {
		            	$rootScope.$broadcast(CONST.EVENTO, 'refreshGrid');
		            	$uibModalInstance.close(vm.formData.model);	 
		            },
		            function(errResponse){
		            	alert(JSON.stringify(errResponse, null,2));
		            });
		               		
	    	       break;       
	    	       
	        default:
	        	   break;
	    		
	        } // switch
        } // function

    function cancel() {
      vm.formData.options.resetModel()
      $uibModalInstance.dismiss('cancel');
    };
  });  
