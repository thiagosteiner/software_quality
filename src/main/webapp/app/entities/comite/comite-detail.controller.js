(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ComiteDetailController', ComiteDetailController);

    ComiteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Comite', 'Professor', 'Documento'];

    function ComiteDetailController($scope, $rootScope, $stateParams, previousState, entity, Comite, Professor, Documento) {
        var vm = this;

        vm.comite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:comiteUpdate', function(event, result) {
            vm.comite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
