(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .controller('WidgetTemplateCategoryController', WidgetTemplateCategoryController);

    WidgetTemplateCategoryController.$inject = ['$scope', '$state', 'DataUtils', 'WidgetTemplateCategory'];

    function WidgetTemplateCategoryController ($scope, $state, DataUtils, WidgetTemplateCategory) {
        var vm = this;
        
        vm.widgetTemplateCategories = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            WidgetTemplateCategory.query(function(result) {
                vm.widgetTemplateCategories = result;
            });
        }
    }
})();
