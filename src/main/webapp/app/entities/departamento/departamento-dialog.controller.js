(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DepartamentoDialogController', DepartamentoDialogController);

    DepartamentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Departamento', 'Professor'];

    function DepartamentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Departamento, Professor) {
        var vm = this;

        vm.departamento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.professors = Professor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.departamento.id !== null) {
                Departamento.update(vm.departamento, onSaveSuccess, onSaveError);
            } else {
                Departamento.save(vm.departamento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:departamentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
