(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DocumentoDetailController', DocumentoDetailController);

    DocumentoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Documento', 'Aluno', 'Comite', 'Professor'];

    function DocumentoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Documento, Aluno, Comite, Professor) {
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