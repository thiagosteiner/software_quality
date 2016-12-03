(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteComiteDetailController', ConviteComiteDetailController);

    ConviteComiteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ConviteComite', 'Professor', 'Comite'];

    function ConviteComiteDetailController($scope, $rootScope, $stateParams, previousState, entity, ConviteComite, Professor, Comite) {
        var vm = this;

        vm.conviteComite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:conviteComiteUpdate', function(event, result) {
            vm.conviteComite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
