(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteOrientadorDialogController', ConviteOrientadorDialogController);

    ConviteOrientadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ConviteOrientador', 'Professor', 'Documento', 'Aluno'];

    function ConviteOrientadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, ConviteOrientador, Professor, Documento, Aluno) {
        var vm = this;

        vm.conviteOrientador = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.orientadorconvidados = Professor.query({filter: 'conviteorientador-is-null'});
        $q.all([vm.conviteOrientador.$promise, vm.orientadorconvidados.$promise]).then(function() {
            if (!vm.conviteOrientador.orientadorconvidadoId) {
                return $q.reject();
            }
            return Professor.get({id : vm.conviteOrientador.orientadorconvidadoId}).$promise;
        }).then(function(orientadorconvidado) {
            vm.orientadorconvidados.push(orientadorconvidado);
        });
        vm.documentos = Documento.query({filter: 'conviteorientador-is-null'});
        $q.all([vm.conviteOrientador.$promise, vm.documentos.$promise]).then(function() {
            if (!vm.conviteOrientador.documentoId) {
                return $q.reject();
            }
            return Documento.get({id : vm.conviteOrientador.documentoId}).$promise;
        }).then(function(documento) {
            vm.documentos.push(documento);
        });
        vm.alunos = Aluno.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.conviteOrientador.id !== null) {
                ConviteOrientador.update(vm.conviteOrientador, onSaveSuccess, onSaveError);
            } else {
                ConviteOrientador.save(vm.conviteOrientador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:conviteOrientadorUpdate', result);
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
