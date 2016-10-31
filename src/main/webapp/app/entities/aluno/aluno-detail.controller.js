(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('AlunoDetailController', AlunoDetailController);

    AlunoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Aluno', 'Tese', 'Proposta', 'Publicacao', 'Professor'];

    function AlunoDetailController($scope, $rootScope, $stateParams, previousState, entity, Aluno, Tese, Proposta, Publicacao, Professor) {
        var vm = this;

        vm.aluno = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:alunoUpdate', function(event, result) {
            vm.aluno = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
