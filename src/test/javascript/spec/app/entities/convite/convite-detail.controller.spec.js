'use strict';

describe('Controller Tests', function() {

    describe('Convite Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConvite, MockAluno, MockProfessor, MockComite, MockDocumento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConvite = jasmine.createSpy('MockConvite');
            MockAluno = jasmine.createSpy('MockAluno');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockComite = jasmine.createSpy('MockComite');
            MockDocumento = jasmine.createSpy('MockDocumento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Convite': MockConvite,
                'Aluno': MockAluno,
                'Professor': MockProfessor,
                'Comite': MockComite,
                'Documento': MockDocumento
            };
            createController = function() {
                $injector.get('$controller')("ConviteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'qsoftwareApp:conviteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
