(function() {
    'use strict';
    angular
        .module('qsoftwareApp')
        .factory('Document', Document);

    Document.$inject = ['$resource', 'DateUtils'];

    function Document ($resource, DateUtils) {
        var resourceUrl =  'api/showdocuments/:id';

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
            }
        });
    }
})();
