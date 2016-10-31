(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ComiteDeleteController',ComiteDeleteController);

    ComiteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Comite'];

    function ComiteDeleteController($uibModalInstance, entity, Comite) {
        var vm = this;

        vm.comite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Comite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
