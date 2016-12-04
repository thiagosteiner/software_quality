(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ProfessorDialogController', ProfessorDialogController);

    ProfessorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Professor', 'User', 'Departamento', 'Documento', 'Comite'];

    function ProfessorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Professor, User, Departamento, Documento, Comite) {
        var vm = this;

        vm.professor = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.departamentos = Departamento.query();
        vm.documentos = Documento.query();
        vm.comites = Comite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.professor.id !== null) {
                Professor.update(vm.professor, onSaveSuccess, onSaveError);
            } else {
                Professor.save(vm.professor, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:professorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
