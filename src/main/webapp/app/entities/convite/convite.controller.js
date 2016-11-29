(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteController', ConviteController);

    ConviteController.$inject = ['$scope', '$state', 'Convite'];

    function ConviteController ($scope, $state, Convite) {
        var vm = this;

        vm.convites = [];

        loadAll();

        function loadAll() {
            Convite.query(function(result) {
                vm.convites = result;
            });
        }
    }
})();
