(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DocumentoController', DocumentoController);

    DocumentoController.$inject = ['$scope', '$state', 'DataUtils', 'Documento'];

    function DocumentoController ($scope, $state, DataUtils, Documento) {
        var vm = this;

        vm.documentos = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Documento.query(function(result) {
                vm.documentos = result;
            });
        }
    }
})();
