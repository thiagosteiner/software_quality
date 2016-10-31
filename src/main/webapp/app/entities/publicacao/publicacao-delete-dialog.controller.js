(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('PublicacaoDeleteController',PublicacaoDeleteController);

    PublicacaoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Publicacao'];

    function PublicacaoDeleteController($uibModalInstance, entity, Publicacao) {
        var vm = this;

        vm.publicacao = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Publicacao.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
