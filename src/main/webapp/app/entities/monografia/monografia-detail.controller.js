(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('MonografiaDetailController', MonografiaDetailController);

    MonografiaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Monografia', 'Comite'];

    function MonografiaDetailController($scope, $rootScope, $stateParams, previousState, entity, Monografia, Comite) {
        var vm = this;

        vm.monografia = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:monografiaUpdate', function(event, result) {
            vm.monografia = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
