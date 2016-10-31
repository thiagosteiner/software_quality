(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DepartamentoController', DepartamentoController);

    DepartamentoController.$inject = ['$scope', '$state', 'Departamento'];

    function DepartamentoController ($scope, $state, Departamento) {
        var vm = this;
        
        vm.departamentos = [];

        loadAll();

        function loadAll() {
            Departamento.query(function(result) {
                vm.departamentos = result;
            });
        }
    }
})();
