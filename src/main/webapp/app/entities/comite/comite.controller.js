(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ComiteController', ComiteController);

    ComiteController.$inject = ['$scope', '$state', 'Comite'];

    function ComiteController ($scope, $state, Comite) {
        var vm = this;
        
        vm.comites = [];

        loadAll();

        function loadAll() {
            Comite.query(function(result) {
                vm.comites = result;
            });
        }
    }
})();
