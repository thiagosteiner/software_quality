(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('MonografiaController', MonografiaController);

    MonografiaController.$inject = ['$scope', '$state', 'Monografia'];

    function MonografiaController ($scope, $state, Monografia) {
        var vm = this;
        
        vm.monografias = [];

        loadAll();

        function loadAll() {
            Monografia.query(function(result) {
                vm.monografias = result;
            });
        }
    }
})();
