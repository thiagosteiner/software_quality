(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteDialogController', ConviteDialogController);

    ConviteDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Convite', 'Aluno', 'Professor', 'Comite', 'Documento'];

    function ConviteDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Convite, Aluno, Professor, Comite, Documento) {
        var vm = this;

        vm.convite = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.alunos = Aluno.query();
        vm.professors = Professor.query();
        vm.conviteprofessors = Professor.query({filter: 'convite-is-null'});
        $q.all([vm.convite.$promise, vm.conviteprofessors.$promise]).then(function() {
            if (!vm.convite.conviteprofessorId) {
                return $q.reject();
            }
            return Professor.get({id : vm.convite.conviteprofessorId}).$promise;
        }).then(function(conviteprofessor) {
            vm.conviteprofessors.push(conviteprofessor);
        });
        vm.convitecomites = Comite.query({filter: 'convite-is-null'});
        $q.all([vm.convite.$promise, vm.convitecomites.$promise]).then(function() {
            if (!vm.convite.conviteComiteId) {
                return $q.reject();
            }
            return Comite.get({id : vm.convite.conviteComiteId}).$promise;
        }).then(function(conviteComite) {
            vm.convitecomites.push(conviteComite);
        });
        vm.convitedocumentos = Documento.query({filter: 'convite-is-null'});
        $q.all([vm.convite.$promise, vm.convitedocumentos.$promise]).then(function() {
            if (!vm.convite.conviteDocumentoId) {
                return $q.reject();
            }
            return Documento.get({id : vm.convite.conviteDocumentoId}).$promise;
        }).then(function(conviteDocumento) {
            vm.convitedocumentos.push(conviteDocumento);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.convite.id !== null) {
                Convite.update(vm.convite, onSaveSuccess, onSaveError);
            } else {
                Convite.save(vm.convite, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:conviteUpdate', result);
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
