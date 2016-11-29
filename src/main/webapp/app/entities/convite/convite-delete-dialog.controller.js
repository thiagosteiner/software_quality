(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteDeleteController',ConviteDeleteController);

    ConviteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Convite'];

    function ConviteDeleteController($uibModalInstance, entity, Convite) {
        var vm = this;

        vm.convite = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Convite.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
