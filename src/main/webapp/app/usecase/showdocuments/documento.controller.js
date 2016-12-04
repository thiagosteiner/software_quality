(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('DocumentController', DocumentController);

    DocumentController.$inject = ['$scope', '$state', 'DataUtils', 'Document'];

    function DocumentController ($scope, $state, DataUtils, Documento) {
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
