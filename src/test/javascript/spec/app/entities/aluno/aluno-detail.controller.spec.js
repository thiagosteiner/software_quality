'use strict';

describe('Controller Tests', function() {

    describe('Aluno Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAluno, MockDocumento, MockConvite;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAluno = jasmine.createSpy('MockAluno');
            MockDocumento = jasmine.createSpy('MockDocumento');
            MockConvite = jasmine.createSpy('MockConvite');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Aluno': MockAluno,
                'Documento': MockDocumento,
                'Convite': MockConvite
            };
            createController = function() {
                $injector.get('$controller')("AlunoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'qsoftwareApp:alunoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});