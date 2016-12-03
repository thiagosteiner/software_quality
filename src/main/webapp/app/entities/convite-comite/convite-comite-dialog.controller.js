(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteComiteDialogController', ConviteComiteDialogController);

    ConviteComiteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ConviteComite', 'Professor', 'Comite'];

    function ConviteComiteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ConviteComite, Professor, Comite) {
        var vm = this;

        vm.conviteComite = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.professorconvidadocomites = Professor.query({filter: 'convitecomite-is-null'});
        $q.all([vm.conviteComite.$promise, vm.professorconvidadocomites.$promise]).then(function() {
            if (!vm.conviteComite.professorconvidadocomiteId) {
                return $q.reject();
            }
            return Professor.get({id : vm.conviteComite.professorconvidadocomiteId}).$promise;
        }).then(function(professorconvidadocomite) {
            vm.professorconvidadocomites.push(professorconvidadocomite);
        });
        vm.comites = Comite.query({filter: 'convitecomite-is-null'});
        $q.all([vm.conviteComite.$promise, vm.comites.$promise]).then(function() {
            if (!vm.conviteComite.comiteId) {
                return $q.reject();
            }
            return Comite.get({id : vm.conviteComite.comiteId}).$promise;
        }).then(function(comite) {
            vm.comites.push(comite);
        });
        vm.professors = Professor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.conviteComite.id !== null) {
                ConviteComite.update(vm.conviteComite, onSaveSuccess, onSaveError);
            } else {
                ConviteComite.save(vm.conviteComite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:conviteComiteUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataCriacao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
