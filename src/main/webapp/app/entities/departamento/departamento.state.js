(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('departamento', {
            parent: 'entity',
            url: '/departamento',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Departamentos'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/departamento/departamentos.html',
                    controller: 'DepartamentoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('departamento-detail', {
            parent: 'entity',
            url: '/departamento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Departamento'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/departamento/departamento-detail.html',
                    controller: 'DepartamentoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Departamento', function($stateParams, Departamento) {
                    return Departamento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'departamento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('departamento-detail.edit', {
            parent: 'departamento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/departamento/departamento-dialog.html',
                    controller: 'DepartamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Departamento', function(Departamento) {
                            return Departamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('departamento.new', {
            parent: 'departamento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/departamento/departamento-dialog.html',
                    controller: 'DepartamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nome: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('departamento', null, { reload: 'departamento' });
                }, function() {
                    $state.go('departamento');
                });
            }]
        })
        .state('departamento.edit', {
            parent: 'departamento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/departamento/departamento-dialog.html',
                    controller: 'DepartamentoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Departamento', function(Departamento) {
                            return Departamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('departamento', null, { reload: 'departamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('departamento.delete', {
            parent: 'departamento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/departamento/departamento-delete-dialog.html',
                    controller: 'DepartamentoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Departamento', function(Departamento) {
                            return Departamento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('departamento', null, { reload: 'departamento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
