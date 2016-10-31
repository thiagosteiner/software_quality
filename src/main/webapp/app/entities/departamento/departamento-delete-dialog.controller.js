(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DepartamentoDeleteController',DepartamentoDeleteController);

    DepartamentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Departamento'];

    function DepartamentoDeleteController($uibModalInstance, entity, Departamento) {
        var vm = this;

        vm.departamento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Departamento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
