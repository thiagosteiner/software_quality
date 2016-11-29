(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('convite', {
            parent: 'entity',
            url: '/convite',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Convites'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convite/convites.html',
                    controller: 'ConviteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('convite-detail', {
            parent: 'entity',
            url: '/convite/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Convite'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convite/convite-detail.html',
                    controller: 'ConviteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Convite', function($stateParams, Convite) {
                    return Convite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'convite',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('convite-detail.edit', {
            parent: 'convite-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite/convite-dialog.html',
                    controller: 'ConviteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Convite', function(Convite) {
                            return Convite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convite.new', {
            parent: 'convite',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite/convite-dialog.html',
                    controller: 'ConviteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                dataCriacao: null,
                                tipoConvite: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('convite', null, { reload: 'convite' });
                }, function() {
                    $state.go('convite');
                });
            }]
        })
        .state('convite.edit', {
            parent: 'convite',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite/convite-dialog.html',
                    controller: 'ConviteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Convite', function(Convite) {
                            return Convite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convite', null, { reload: 'convite' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convite.delete', {
            parent: 'convite',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite/convite-delete-dialog.html',
                    controller: 'ConviteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Convite', function(Convite) {
                            return Convite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convite', null, { reload: 'convite' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
