(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .controller('WidgetTemplateDetailController', WidgetTemplateDetailController);

    WidgetTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'WidgetTemplate', 'User', 'WidgetTemplateCategory'];

    function WidgetTemplateDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, WidgetTemplate, User, WidgetTemplateCategory) {
        var vm = this;

        vm.widgetTemplate = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('webDashCwApp:widgetTemplateUpdate', function(event, result) {
            vm.widgetTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
