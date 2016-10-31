(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('publicacao', {
            parent: 'entity',
            url: '/publicacao',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Publicacaos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publicacao/publicacaos.html',
                    controller: 'PublicacaoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('publicacao-detail', {
            parent: 'entity',
            url: '/publicacao/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Publicacao'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/publicacao/publicacao-detail.html',
                    controller: 'PublicacaoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Publicacao', function($stateParams, Publicacao) {
                    return Publicacao.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'publicacao',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('publicacao-detail.edit', {
            parent: 'publicacao-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacao/publicacao-dialog.html',
                    controller: 'PublicacaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Publicacao', function(Publicacao) {
                            return Publicacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('publicacao.new', {
            parent: 'publicacao',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacao/publicacao-dialog.html',
                    controller: 'PublicacaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                dataPublicacao: null,
                                dataApresentacao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('publicacao', null, { reload: 'publicacao' });
                }, function() {
                    $state.go('publicacao');
                });
            }]
        })
        .state('publicacao.edit', {
            parent: 'publicacao',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacao/publicacao-dialog.html',
                    controller: 'PublicacaoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Publicacao', function(Publicacao) {
                            return Publicacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('publicacao', null, { reload: 'publicacao' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('publicacao.delete', {
            parent: 'publicacao',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/publicacao/publicacao-delete-dialog.html',
                    controller: 'PublicacaoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Publicacao', function(Publicacao) {
                            return Publicacao.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('publicacao', null, { reload: 'publicacao' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
