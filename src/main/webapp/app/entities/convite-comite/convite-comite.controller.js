(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteComiteController', ConviteComiteController);

    ConviteComiteController.$inject = ['$scope', '$state', 'ConviteComite'];

    function ConviteComiteController ($scope, $state, ConviteComite) {
        var vm = this;

        vm.conviteComites = [];

        loadAll();

        function loadAll() {
            ConviteComite.query(function(result) {
                vm.conviteComites = result;
                vm.searchQuery = null;
            });
        }
    }
})();
