(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('PublicacaoDetailController', PublicacaoDetailController);

    PublicacaoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Publicacao'];

    function PublicacaoDetailController($scope, $rootScope, $stateParams, previousState, entity, Publicacao) {
        var vm = this;

        vm.publicacao = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:publicacaoUpdate', function(event, result) {
            vm.publicacao = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
