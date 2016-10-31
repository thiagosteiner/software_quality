(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('TeseDetailController', TeseDetailController);

    TeseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tese', 'Comite'];

    function TeseDetailController($scope, $rootScope, $stateParams, previousState, entity, Tese, Comite) {
        var vm = this;

        vm.tese = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:teseUpdate', function(event, result) {
            vm.tese = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
