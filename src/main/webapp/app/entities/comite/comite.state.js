(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('comite', {
            parent: 'entity',
            url: '/comite',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Comites'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comite/comites.html',
                    controller: 'ComiteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('comite-detail', {
            parent: 'entity',
            url: '/comite/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Comite'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/comite/comite-detail.html',
                    controller: 'ComiteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Comite', function($stateParams, Comite) {
                    return Comite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'comite',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('comite-detail.edit', {
            parent: 'comite-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comite/comite-dialog.html',
                    controller: 'ComiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comite', function(Comite) {
                            return Comite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comite.new', {
            parent: 'comite',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comite/comite-dialog.html',
                    controller: 'ComiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                local: null,
                                tipo: null,
                                dataOcorrencia: null,
                                ataComite: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('comite', null, { reload: 'comite' });
                }, function() {
                    $state.go('comite');
                });
            }]
        })
        .state('comite.edit', {
            parent: 'comite',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comite/comite-dialog.html',
                    controller: 'ComiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Comite', function(Comite) {
                            return Comite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comite', null, { reload: 'comite' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('comite.delete', {
            parent: 'comite',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/comite/comite-delete-dialog.html',
                    controller: 'ComiteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Comite', function(Comite) {
                            return Comite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('comite', null, { reload: 'comite' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
