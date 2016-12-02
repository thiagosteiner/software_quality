(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ConviteOrientadorDetailController', ConviteOrientadorDetailController);

    ConviteOrientadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ConviteOrientador', 'Professor', 'Documento', 'Aluno'];

    function ConviteOrientadorDetailController($scope, $rootScope, $stateParams, previousState, entity, ConviteOrientador, Professor, Documento, Aluno) {
        var vm = this;

        vm.conviteOrientador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:conviteOrientadorUpdate', function(event, result) {
            vm.conviteOrientador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
