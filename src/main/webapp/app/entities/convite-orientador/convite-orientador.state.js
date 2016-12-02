(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('convite-orientador', {
            parent: 'entity',
            url: '/convite-orientador',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ConviteOrientadors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convite-orientador/convite-orientadors.html',
                    controller: 'ConviteOrientadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('convite-orientador-detail', {
            parent: 'entity',
            url: '/convite-orientador/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ConviteOrientador'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convite-orientador/convite-orientador-detail.html',
                    controller: 'ConviteOrientadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ConviteOrientador', function($stateParams, ConviteOrientador) {
                    return ConviteOrientador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'convite-orientador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('convite-orientador-detail.edit', {
            parent: 'convite-orientador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite-orientador/convite-orientador-dialog.html',
                    controller: 'ConviteOrientadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConviteOrientador', function(ConviteOrientador) {
                            return ConviteOrientador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convite-orientador.new', {
            parent: 'convite-orientador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite-orientador/convite-orientador-dialog.html',
                    controller: 'ConviteOrientadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                dataCriacao: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('convite-orientador', null, { reload: 'convite-orientador' });
                }, function() {
                    $state.go('convite-orientador');
                });
            }]
        })
        .state('convite-orientador.edit', {
            parent: 'convite-orientador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite-orientador/convite-orientador-dialog.html',
                    controller: 'ConviteOrientadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConviteOrientador', function(ConviteOrientador) {
                            return ConviteOrientador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convite-orientador', null, { reload: 'convite-orientador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convite-orientador.delete', {
            parent: 'convite-orientador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite-orientador/convite-orientador-delete-dialog.html',
                    controller: 'ConviteOrientadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ConviteOrientador', function(ConviteOrientador) {
                            return ConviteOrientador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convite-orientador', null, { reload: 'convite-orientador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
