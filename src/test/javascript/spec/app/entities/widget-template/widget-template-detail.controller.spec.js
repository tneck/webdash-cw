'use strict';

describe('Controller Tests', function() {

    describe('WidgetTemplate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWidgetTemplate, MockUser, MockWidgetTemplateCategory;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWidgetTemplate = jasmine.createSpy('MockWidgetTemplate');
            MockUser = jasmine.createSpy('MockUser');
            MockWidgetTemplateCategory = jasmine.createSpy('MockWidgetTemplateCategory');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'WidgetTemplate': MockWidgetTemplate,
                'User': MockUser,
                'WidgetTemplateCategory': MockWidgetTemplateCategory
            };
            createController = function() {
                $injector.get('$controller')("WidgetTemplateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'webDashCwApp:widgetTemplateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
