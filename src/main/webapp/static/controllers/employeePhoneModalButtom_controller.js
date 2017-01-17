  app.controller('EmployeePhoneModalButtomCtrl', function (
													  $scope, 
													  $uibModalInstance, 
													  formData, 
													  $log, 
													  EmployeeService,
													  PhoneService,
													  $rootScope, 
													  CONST,
													  shareAccion, 
													  ListaTelefonos) {
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
    	$log.log("EmployeePhoneModalButtomCtrl model:" + JSON.stringify(vm.formData.model, null,2));
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
    
    function findIndexInData(data, property, value) {
        var result = -1;
        data.some(function (item, i) {
            if (item[property] === value) {
                result = i;
                return true;
            }
        });
        return result;
    }
    
	    function aplicarAccion (accion) {
	    	switch (accion){
	    	case  'Update':
	    		   vm.form.$invalid = true;
	    		   var employee = ListaTelefonos.getObject();
	    		   
/*	    		   var index = findIndexInData (employee.phoneNumbers, 'id', formData.model.id);	    		   
	    		   employee.phoneNumbers[index] = JSON.parse(JSON.stringify(formData.model));*/
	               formData.model.employee = {id : employee.id};
	    		   PhoneService.updatePhone(formData.model, formData.model.id)
	    	       .then( 
	    	       function(d) {
	    	    	   vm.form.$invalid = false;
/*		          	   ListaTelefonos.copyToList(d.phoneNumbers);
		          	   ListaTelefonos.setObject(d);*/
	    	    	   $rootScope.$broadcast(CONST.EVENTO, 'refreshGrid'); 
	    	    	   $uibModalInstance.close(vm.formData.model);
	    	       },
	    	       function(errResponse){
	    	    	  vm.form.$invalid = false; 
	    	    	  alert(JSON.stringify(errResponse, null,2));
	    	       }); 	
	                  
	               break;
	    	case 'Add':
	    		   vm.form.$invalid = true;
	    		   var employee = ListaTelefonos.getObject();
	    		   // se asigna employee al telefono
	    		   //formData.model.employee = {id : employee.id};

	    		   PhoneService.createPhone(formData.model, employee.id)
	    	       .then( 
	    	       function(d) {
	    	    	   vm.form.$invalid = false;
	    	    	   $rootScope.$broadcast(CONST.EVENTO, 'refreshGrid'); 
	    	    	   $uibModalInstance.close(vm.formData.model);
	    	       },
	    	       function(errResponse){
	    	    	  vm.form.$invalid = false; 
	    	    	  alert(JSON.stringify(errResponse, null,2));
	    	       });
	    		   
	    	       break;
	    	       
	    	case 'Delete':
	    		   vm.form.$invalid = true;
	    		   var employee = ListaTelefonos.getObject();	  

	    		   PhoneService.deletePhone(formData.model.id)
		            .then(
		            function(d) {
		            	vm.form.$invalid = false;
		            	$rootScope.$broadcast(CONST.EVENTO, 'refreshGrid');
		            	$uibModalInstance.close(vm.formData.model);	 
		            },
		            function(errResponse){
		            	vm.form.$invalid = false;
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
