(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteDetailController', ConviteDetailController);

    ConviteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Convite', 'Aluno', 'Professor', 'Comite', 'Documento'];

    function ConviteDetailController($scope, $rootScope, $stateParams, previousState, entity, Convite, Aluno, Professor, Comite, Documento) {
        var vm = this;

        vm.convite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:conviteUpdate', function(event, result) {
            vm.convite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
