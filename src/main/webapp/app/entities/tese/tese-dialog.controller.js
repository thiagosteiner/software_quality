(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('TeseDialogController', TeseDialogController);

    TeseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Tese', 'Comite'];

    function TeseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Tese, Comite) {
        var vm = this;

        vm.tese = entity;
        vm.clear = clear;
        vm.save = save;
        vm.comites = Comite.query({filter: 'tese-is-null'});
        $q.all([vm.tese.$promise, vm.comites.$promise]).then(function() {
            if (!vm.tese.comiteId) {
                return $q.reject();
            }
            return Comite.get({id : vm.tese.comiteId}).$promise;
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
            if (vm.tese.id !== null) {
                Tese.update(vm.tese, onSaveSuccess, onSaveError);
            } else {
                Tese.save(vm.tese, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:teseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
