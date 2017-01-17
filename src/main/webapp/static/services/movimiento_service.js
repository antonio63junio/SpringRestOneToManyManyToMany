'use strict';

angular.module('app').factory('MovimientoService', ['CONST', '$http', '$q', function(CONST, $http, $q){
    
    var factory = {
    		getTotalRows: getTotalRows,
    		getMovimientos:getMovimientos

    };

    return factory;

    function getTotalRows() {
        var deferred = $q.defer();
        $http.get(CONST.REST_SERVICE_URI_BASE + '/movimientocount/')
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while calculating number of pages');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
    
	function getMovimientos (pageNumber,size, columnSort, sentido) {
        var deferred = $q.defer();
        $http.get(CONST.REST_SERVICE_URI_BASE + '/movimientopage/' + '?page='+pageNumber+
        		 '&size='+size+'&columnSort='+columnSort+'&sentido='+sentido)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching movimientos');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
	}
    

    


}]);
