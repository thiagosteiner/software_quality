(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DocumentoDialogController', DocumentoDialogController);

    DocumentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Documento', 'Aluno', 'Comite', 'Professor'];

    function DocumentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Documento, Aluno, Comite, Professor) {
        var vm = this;

        vm.documento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.alunos = Aluno.query();
        vm.documentocomites = Comite.query({filter: 'documento-is-null'});
        $q.all([vm.documento.$promise, vm.documentocomites.$promise]).then(function() {
            if (!vm.documento.documentocomiteId) {
                return $q.reject();
            }
            return Comite.get({id : vm.documento.documentocomiteId}).$promise;
        }).then(function(documentocomite) {
            vm.documentocomites.push(documentocomite);
        });
        vm.documentoorientadors = Professor.query({filter: 'documento-is-null'});
        $q.all([vm.documento.$promise, vm.documentoorientadors.$promise]).then(function() {
            if (!vm.documento.documentoorientadorId) {
                return $q.reject();
            }
            return Professor.get({id : vm.documento.documentoorientadorId}).$promise;
        }).then(function(documentoorientador) {
            vm.documentoorientadors.push(documentoorientador);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.documento.id !== null) {
                Documento.update(vm.documento, onSaveSuccess, onSaveError);
            } else {
                Documento.save(vm.documento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:documentoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dataCriacao = false;

        vm.setArquivo = function ($file, documento) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        documento.arquivo = base64Data;
                        documento.arquivoContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
