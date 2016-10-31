(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('AlunoDialogController', AlunoDialogController);

    AlunoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Aluno', 'Monografia', 'Proposta', 'Publicacao', 'Professor'];

    function AlunoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Aluno, Monografia, Proposta, Publicacao, Professor) {
        var vm = this;

        vm.aluno = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.monografias = Monografia.query({filter: 'aluno-is-null'});
        $q.all([vm.aluno.$promise, vm.monografias.$promise]).then(function() {
            if (!vm.aluno.monografiaId) {
                return $q.reject();
            }
            return Monografia.get({id : vm.aluno.monografiaId}).$promise;
        }).then(function(monografia) {
            vm.monografias.push(monografia);
        });
        vm.propostas = Proposta.query();
        vm.publicacaos = Publicacao.query();
        vm.professors = Professor.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.aluno.id !== null) {
                Aluno.update(vm.aluno, onSaveSuccess, onSaveError);
            } else {
                Aluno.save(vm.aluno, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:alunoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.previsaoFormatura = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
