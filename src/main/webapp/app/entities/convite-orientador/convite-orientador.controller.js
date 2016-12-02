(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteOrientadorController', ConviteOrientadorController);

    ConviteOrientadorController.$inject = ['$scope', '$state', 'ConviteOrientador'];

    function ConviteOrientadorController ($scope, $state, ConviteOrientador) {
        var vm = this;

        vm.conviteOrientadors = [];

        loadAll();

        function loadAll() {
            ConviteOrientador.query(function(result) {
                vm.conviteOrientadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
