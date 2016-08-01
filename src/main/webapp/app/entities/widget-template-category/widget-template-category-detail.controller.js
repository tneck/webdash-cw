(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .controller('WidgetTemplateCategoryDetailController', WidgetTemplateCategoryDetailController);

    WidgetTemplateCategoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'WidgetTemplateCategory'];

    function WidgetTemplateCategoryDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, WidgetTemplateCategory) {
        var vm = this;

        vm.widgetTemplateCategory = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('webDashCwApp:widgetTemplateCategoryUpdate', function(event, result) {
            vm.widgetTemplateCategory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
