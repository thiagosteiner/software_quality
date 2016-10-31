(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('PropostaDetailController', PropostaDetailController);

    PropostaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Proposta', 'Aluno', 'Comite'];

    function PropostaDetailController($scope, $rootScope, $stateParams, previousState, entity, Proposta, Aluno, Comite) {
        var vm = this;

        vm.proposta = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:propostaUpdate', function(event, result) {
            vm.proposta = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
