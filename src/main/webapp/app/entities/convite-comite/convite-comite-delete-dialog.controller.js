(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteComiteDeleteController',ConviteComiteDeleteController);

    ConviteComiteDeleteController.$inject = ['$uibModalInstance', 'entity', 'ConviteComite'];

    function ConviteComiteDeleteController($uibModalInstance, entity, ConviteComite) {
        var vm = this;

        vm.conviteComite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ConviteComite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
