(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('PublicacaoController', PublicacaoController);

    PublicacaoController.$inject = ['$scope', '$state', 'Publicacao'];

    function PublicacaoController ($scope, $state, Publicacao) {
        var vm = this;
        
        vm.publicacaos = [];

        loadAll();

        function loadAll() {
            Publicacao.query(function(result) {
                vm.publicacaos = result;
            });
        }
    }
})();
