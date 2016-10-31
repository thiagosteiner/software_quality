(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('PropostaDialogController', PropostaDialogController);

    PropostaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Proposta', 'Aluno', 'Comite'];

    function PropostaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Proposta, Aluno, Comite) {
        var vm = this;

        vm.proposta = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.alunos = Aluno.query();
        vm.comites = Comite.query({filter: 'proposta-is-null'});
        $q.all([vm.proposta.$promise, vm.comites.$promise]).then(function() {
            if (!vm.proposta.comiteId) {
                return $q.reject();
            }
            return Comite.get({id : vm.proposta.comiteId}).$promise;
        }).then(function(comite) {
            vm.comites.push(comite);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.proposta.id !== null) {
                Proposta.update(vm.proposta, onSaveSuccess, onSaveError);
            } else {
                Proposta.save(vm.proposta, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:propostaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataApresentacao = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
