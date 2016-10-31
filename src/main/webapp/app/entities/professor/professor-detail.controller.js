(function() {
    'use strict';

    angular
        .module('qsoftwareApp')
        .controller('ProfessorDetailController', ProfessorDetailController);

    ProfessorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Professor', 'Aluno', 'Publicacao', 'Departamento'];

    function ProfessorDetailController($scope, $rootScope, $stateParams, previousState, entity, Professor, Aluno, Publicacao, Departamento) {
        var vm = this;

        vm.professor = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('qsoftwareApp:professorUpdate', function(event, result) {
            vm.professor = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
