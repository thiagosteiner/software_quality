(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DocumentoDeleteController',DocumentoDeleteController);

    DocumentoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Documento'];

    function DocumentoDeleteController($uibModalInstance, entity, Documento) {
        var vm = this;

        vm.documento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Documento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
