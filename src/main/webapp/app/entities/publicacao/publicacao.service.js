(function() {
    'use strict';
    angular
        .module('qsoftwareApp')
        .factory('Publicacao', Publicacao);

    Publicacao.$inject = ['$resource', 'DateUtils'];

    function Publicacao ($resource, DateUtils) {
        var resourceUrl =  'api/publicacaos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataPublicacao = DateUtils.convertLocalDateFromServer(data.dataPublicacao);
                        data.dataApresentacao = DateUtils.convertLocalDateFromServer(data.dataApresentacao);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataPublicacao = DateUtils.convertLocalDateToServer(copy.dataPublicacao);
                    copy.dataApresentacao = DateUtils.convertLocalDateToServer(copy.dataApresentacao);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataPublicacao = DateUtils.convertLocalDateToServer(copy.dataPublicacao);
                    copy.dataApresentacao = DateUtils.convertLocalDateToServer(copy.dataApresentacao);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
