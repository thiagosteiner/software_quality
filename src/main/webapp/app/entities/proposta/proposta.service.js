(function() {
    'use strict';
    angular
        .module('qsoftwareApp')
        .factory('Proposta', Proposta);

    Proposta.$inject = ['$resource', 'DateUtils'];

    function Proposta ($resource, DateUtils) {
        var resourceUrl =  'api/propostas/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataApresentacao = DateUtils.convertLocalDateFromServer(data.dataApresentacao);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataApresentacao = DateUtils.convertLocalDateToServer(copy.dataApresentacao);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataApresentacao = DateUtils.convertLocalDateToServer(copy.dataApresentacao);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
