'use strict';

angular.module('app').factory('PhoneService', ['CONST', '$http', '$q', function(CONST, $http, $q){
    

    var REST_SERVICE_URI_PHONE = CONST.REST_SERVICE_URI_BASE + '/phone/';

    var factory = {

        createPhone: createPhone,
        updatePhone:updatePhone,
        deletePhone:deletePhone,

    };

    return factory;


    function createPhone(phone, idEmployee) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI_PHONE+idEmployee, phone)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating Phone');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function updatePhone(phone, id) {
    	var deferred = $q.defer();
        $http.put(REST_SERVICE_URI_PHONE+id, phone)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating Phone');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }

    function deletePhone(id) {  	
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI_PHONE+ id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error en borrado de Phone');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    


}]);
