(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('AlunoController', AlunoController);

    AlunoController.$inject = ['$scope', '$state', 'Aluno'];

    function AlunoController ($scope, $state, Aluno) {
        var vm = this;

        vm.alunos = [];

        loadAll();

        function loadAll() {
            Aluno.query(function(result) {
                vm.alunos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
