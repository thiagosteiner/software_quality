'use strict';

describe('Controller Tests', function() {

    describe('ConviteOrientador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConviteOrientador, MockProfessor, MockDocumento, MockAluno;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConviteOrientador = jasmine.createSpy('MockConviteOrientador');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockDocumento = jasmine.createSpy('MockDocumento');
            MockAluno = jasmine.createSpy('MockAluno');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ConviteOrientador': MockConviteOrientador,
                'Professor': MockProfessor,
                'Documento': MockDocumento,
                'Aluno': MockAluno
            };
            createController = function() {
                $injector.get('$controller')("ConviteOrientadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'qsoftwareApp:conviteOrientadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
