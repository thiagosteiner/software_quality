(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('document', {
            parent: 'entity',
            url: '/document',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Documents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/usecase/showdocuments/documentos.html',
                    controller: 'DocumentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('document-detail', {
            parent: 'entity',
            url: '/document/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Document'
            },
            views: {
                'content@': {
                    templateUrl: 'app/usecase/showdocuments/documento-detail.html',
                    controller: 'DocumentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Documento', function($stateParams, Documento) {
                    return Documento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'documento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }

})();
