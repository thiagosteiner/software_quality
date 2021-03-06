(function() {
    'use strict';
    angular
        .module('qsoftwareApp')
        .factory('Documento', Documento);

    Documento.$inject = ['$resource', 'DateUtils'];

    function Documento ($resource, DateUtils) {
        var resourceUrl =  'api/documentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataCriacao = DateUtils.convertLocalDateFromServer(data.dataCriacao);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataCriacao = DateUtils.convertLocalDateToServer(copy.dataCriacao);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataCriacao = DateUtils.convertLocalDateToServer(copy.dataCriacao);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
