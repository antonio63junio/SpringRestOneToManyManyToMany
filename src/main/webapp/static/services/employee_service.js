'use strict';

angular.module('app').factory('EmployeeService', ['CONST', '$http', '$q', function(CONST, $http, $q){
    
    var REST_SERVICE_URI_EMP = CONST.REST_SERVICE_URI_BASE + '/employee/';
    var REST_SERVICE_URI_EMP_PHONES = CONST.REST_SERVICE_URI_BASE + '/employee/phones/';
    var REST_SERVICE_URI_MOV = CONST.REST_SERVICE_URI_BASE + '/movimiento/';
    var factory = {
        fetchAllEmployees: fetchAllEmployees,
        createEmployee: createEmployee,
        updateEmployee:updateEmployee,
        deleteEmployee:deleteEmployee,
        fetchAllMovimientos: fetchAllMovimientos,
        getEmployeeById: getEmployeeById,
        fetchEmployeePhones: fetchEmployeePhones
    };

    return factory;

    function getEmployeeById(id) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_EMP+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Employee');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function fetchAllEmployees() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_EMP)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Employee');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function fetchEmployeePhones(id) {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_EMP_PHONES+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching EmployeeS phones');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function createEmployee(employee) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI_EMP, employee)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error(errResponse);
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function updateEmployee(employee, id) {
    	var deferred = $q.defer();
        $http.put(REST_SERVICE_URI_EMP+id, employee)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deleteEmployee(employee) {  	
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI_EMP+ employee.ssn)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error en borrado de empleado');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
    function fetchAllMovimientos() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI_MOV)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

}]);
