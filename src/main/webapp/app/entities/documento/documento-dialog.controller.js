(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DocumentoDialogController', DocumentoDialogController);

    DocumentoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'DataUtils', 'entity', 'Documento', 'Comite', 'Professor', 'Aluno'];

    function DocumentoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, DataUtils, entity, Documento, Comite, Professor, Aluno) {
        var vm = this;

        vm.documento = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.comites = Comite.query({filter: 'documento(titulo)-is-null'});
        $q.all([vm.documento.$promise, vm.comites.$promise]).then(function() {
            if (!vm.documento.comiteId) {
                return $q.reject();
            }
            return Comite.get({id : vm.documento.comiteId}).$promise;
        }).then(function(comite) {
            vm.comites.push(comite);
        });
        vm.professors = Professor.query();
        vm.alunos = Aluno.query();

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
