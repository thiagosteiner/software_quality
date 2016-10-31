(function() {
    'use strict';
    angular
        .module('qsoftwareApp')
        .factory('Comite', Comite);

    Comite.$inject = ['$resource', 'DateUtils'];

    function Comite ($resource, DateUtils) {
        var resourceUrl =  'api/comites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataOcorrencia = DateUtils.convertLocalDateFromServer(data.dataOcorrencia);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataOcorrencia = DateUtils.convertLocalDateToServer(copy.dataOcorrencia);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataOcorrencia = DateUtils.convertLocalDateToServer(copy.dataOcorrencia);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
