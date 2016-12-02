'use strict';

describe('Controller Tests', function() {

    describe('Professor Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProfessor, MockUser, MockDepartamento, MockComite, MockDocumento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProfessor = jasmine.createSpy('MockProfessor');
            MockUser = jasmine.createSpy('MockUser');
            MockDepartamento = jasmine.createSpy('MockDepartamento');
            MockComite = jasmine.createSpy('MockComite');
            MockDocumento = jasmine.createSpy('MockDocumento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Professor': MockProfessor,
                'User': MockUser,
                'Departamento': MockDepartamento,
                'Comite': MockComite,
                'Documento': MockDocumento
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
