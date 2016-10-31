(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DepartamentoDetailController', DepartamentoDetailController);

    DepartamentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Departamento', 'Professor'];

    function DepartamentoDetailController($scope, $rootScope, $stateParams, previousState, entity, Departamento, Professor) {
        var vm = this;

        vm.departamento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:departamentoUpdate', function(event, result) {
            vm.departamento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
