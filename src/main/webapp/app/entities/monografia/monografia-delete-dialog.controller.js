(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('MonografiaDeleteController',MonografiaDeleteController);

    MonografiaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Monografia'];

    function MonografiaDeleteController($uibModalInstance, entity, Monografia) {
        var vm = this;

        vm.monografia = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Monografia.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
