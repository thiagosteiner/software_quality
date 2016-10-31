(function() {
    'use strict';
    angular
        .module('qsoftwareApp')
        .factory('Departamento', Departamento);

    Departamento.$inject = ['$resource'];

    function Departamento ($resource) {
        var resourceUrl =  'api/departamentos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
