(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ProfessorController', ProfessorController);

    ProfessorController.$inject = ['$scope', '$state', 'Professor'];

    function ProfessorController ($scope, $state, Professor) {
        var vm = this;

        vm.professors = [];

        loadAll();

        function loadAll() {
            Professor.query(function(result) {
                vm.professors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
