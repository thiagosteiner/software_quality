(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ComiteDialogController', ComiteDialogController);

    ComiteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Comite', 'Professor', 'Documento'];

    function ComiteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Comite, Professor, Documento) {
        var vm = this;

        vm.comite = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.professors = Professor.query();
        vm.documentos = Documento.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.comite.id !== null) {
                Comite.update(vm.comite, onSaveSuccess, onSaveError);
            } else {
                Comite.save(vm.comite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:comiteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataOcorrencia = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
