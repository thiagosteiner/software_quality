'use strict';

describe('Controller Tests', function() {

    describe('Documento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDocumento, MockProfessor, MockAluno, MockComite;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDocumento = jasmine.createSpy('MockDocumento');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockAluno = jasmine.createSpy('MockAluno');
            MockComite = jasmine.createSpy('MockComite');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Documento': MockDocumento,
                'Professor': MockProfessor,
                'Aluno': MockAluno,
                'Comite': MockComite
            };
            createController = function() {
                $injector.get('$controller')("DocumentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'qsoftwareApp:documentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
