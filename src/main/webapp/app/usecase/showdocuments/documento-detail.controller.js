(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DocumentDetailController', DocumentDetailController);

    DocumentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Documento', 'Aluno', 'Comite', 'Professor'];

    function DocumentDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Documento, Aluno, Comite, Professor) {
        var vm = this;

        vm.documento = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('qsoftwareApp:documentoUpdate', function(event, result) {
            vm.documento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
