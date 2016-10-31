(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('monografia', {
            parent: 'entity',
            url: '/monografia',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Monografias'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/monografia/monografias.html',
                    controller: 'MonografiaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('monografia-detail', {
            parent: 'entity',
            url: '/monografia/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Monografia'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/monografia/monografia-detail.html',
                    controller: 'MonografiaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Monografia', function($stateParams, Monografia) {
                    return Monografia.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'monografia',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('monografia-detail.edit', {
            parent: 'monografia-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monografia/monografia-dialog.html',
                    controller: 'MonografiaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Monografia', function(Monografia) {
                            return Monografia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('monografia.new', {
            parent: 'monografia',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monografia/monografia-dialog.html',
                    controller: 'MonografiaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                tipo: null,
                                tema: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('monografia', null, { reload: 'monografia' });
                }, function() {
                    $state.go('monografia');
                });
            }]
        })
        .state('monografia.edit', {
            parent: 'monografia',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monografia/monografia-dialog.html',
                    controller: 'MonografiaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Monografia', function(Monografia) {
                            return Monografia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('monografia', null, { reload: 'monografia' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('monografia.delete', {
            parent: 'monografia',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/monografia/monografia-delete-dialog.html',
                    controller: 'MonografiaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Monografia', function(Monografia) {
                            return Monografia.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('monografia', null, { reload: 'monografia' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
