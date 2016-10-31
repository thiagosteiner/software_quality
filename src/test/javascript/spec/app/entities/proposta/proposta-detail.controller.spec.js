'use strict';

describe('Controller Tests', function() {

    describe('Proposta Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProposta, MockAluno, MockComite;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProposta = jasmine.createSpy('MockProposta');
            MockAluno = jasmine.createSpy('MockAluno');
            MockComite = jasmine.createSpy('MockComite');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Proposta': MockProposta,
                'Aluno': MockAluno,
                'Comite': MockComite
            };
            createController = function() {
                $injector.get('$controller')("PropostaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'qsoftwareApp:propostaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
