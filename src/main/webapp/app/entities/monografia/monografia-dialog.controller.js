(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('MonografiaDialogController', MonografiaDialogController);

    MonografiaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Monografia', 'Comite'];

    function MonografiaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Monografia, Comite) {
        var vm = this;

        vm.monografia = entity;
        vm.clear = clear;
        vm.save = save;
        vm.comites = Comite.query({filter: 'monografia-is-null'});
        $q.all([vm.monografia.$promise, vm.comites.$promise]).then(function() {
            if (!vm.monografia.comiteId) {
                return $q.reject();
            }
            return Comite.get({id : vm.monografia.comiteId}).$promise;
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
            if (vm.monografia.id !== null) {
                Monografia.update(vm.monografia, onSaveSuccess, onSaveError);
            } else {
                Monografia.save(vm.monografia, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('qsoftwareApp:monografiaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
