(function() {
    'use strict';
    angular
        .module('qsoftwareApp')
        .factory('Aluno', Aluno);

    Aluno.$inject = ['$resource', 'DateUtils'];

    function Aluno ($resource, DateUtils) {
        var resourceUrl =  'api/alunos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dataIngresso = DateUtils.convertLocalDateFromServer(data.dataIngresso);
                        data.previsaoFormatura = DateUtils.convertLocalDateFromServer(data.previsaoFormatura);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataIngresso = DateUtils.convertLocalDateToServer(copy.dataIngresso);
                    copy.previsaoFormatura = DateUtils.convertLocalDateToServer(copy.previsaoFormatura);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dataIngresso = DateUtils.convertLocalDateToServer(copy.dataIngresso);
                    copy.previsaoFormatura = DateUtils.convertLocalDateToServer(copy.previsaoFormatura);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
