'use strict';

describe('Controller Tests', function() {

    describe('ConviteComite Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConviteComite, MockProfessor, MockComite;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConviteComite = jasmine.createSpy('MockConviteComite');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockComite = jasmine.createSpy('MockComite');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ConviteComite': MockConviteComite,
                'Professor': MockProfessor,
                'Comite': MockComite
            };
            createController = function() {
                $injector.get('$controller')("ConviteComiteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'qsoftwareApp:conviteComiteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
