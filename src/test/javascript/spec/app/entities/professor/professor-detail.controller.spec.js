'use strict';

describe('Controller Tests', function() {

    describe('Professor Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProfessor, MockAluno, MockPublicacao, MockDepartamento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockAluno = jasmine.createSpy('MockAluno');
            MockPublicacao = jasmine.createSpy('MockPublicacao');
            MockDepartamento = jasmine.createSpy('MockDepartamento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Professor': MockProfessor,
                'Aluno': MockAluno,
                'Publicacao': MockPublicacao,
                'Departamento': MockDepartamento
            };
            createController = function() {
                $injector.get('$controller')("ProfessorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'qsoftwareApp:professorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
