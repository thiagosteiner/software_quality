(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('convite-comite', {
            parent: 'entity',
            url: '/convite-comite',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ConviteComites'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convite-comite/convite-comites.html',
                    controller: 'ConviteComiteController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('convite-comite-detail', {
            parent: 'entity',
            url: '/convite-comite/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ConviteComite'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/convite-comite/convite-comite-detail.html',
                    controller: 'ConviteComiteDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ConviteComite', function($stateParams, ConviteComite) {
                    return ConviteComite.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'convite-comite',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('convite-comite-detail.edit', {
            parent: 'convite-comite-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite-comite/convite-comite-dialog.html',
                    controller: 'ConviteComiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConviteComite', function(ConviteComite) {
                            return ConviteComite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convite-comite.new', {
            parent: 'convite-comite',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite-comite/convite-comite-dialog.html',
                    controller: 'ConviteComiteDialogController',
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
                    $state.go('convite-comite', null, { reload: 'convite-comite' });
                }, function() {
                    $state.go('convite-comite');
                });
            }]
        })
        .state('convite-comite.edit', {
            parent: 'convite-comite',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite-comite/convite-comite-dialog.html',
                    controller: 'ConviteComiteDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ConviteComite', function(ConviteComite) {
                            return ConviteComite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convite-comite', null, { reload: 'convite-comite' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('convite-comite.delete', {
            parent: 'convite-comite',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/convite-comite/convite-comite-delete-dialog.html',
                    controller: 'ConviteComiteDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ConviteComite', function(ConviteComite) {
                            return ConviteComite.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('convite-comite', null, { reload: 'convite-comite' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
