(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteOrientadorDeleteController',ConviteOrientadorDeleteController);

    ConviteOrientadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'ConviteOrientador'];

    function ConviteOrientadorDeleteController($uibModalInstance, entity, ConviteOrientador) {
        var vm = this;

        vm.conviteOrientador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ConviteOrientador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
