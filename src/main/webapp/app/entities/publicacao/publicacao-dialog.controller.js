(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('PublicacaoDialogController', PublicacaoDialogController);

    PublicacaoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Publicacao'];

    function PublicacaoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Publicacao) {
        var vm = this;

        vm.publicacao = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.publicacao.id !== null) {
                Publicacao.update(vm.publicacao, onSaveSuccess, onSaveError);
            } else {
                Publicacao.save(vm.publicacao, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:publicacaoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataPublicacao = false;
        vm.datePickerOpenStatus.dataApresentacao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
