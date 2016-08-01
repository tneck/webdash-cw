(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .controller('WidgetTemplateCategoryDeleteController',WidgetTemplateCategoryDeleteController);

    WidgetTemplateCategoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'WidgetTemplateCategory'];

    function WidgetTemplateCategoryDeleteController($uibModalInstance, entity, WidgetTemplateCategory) {
        var vm = this;

        vm.widgetTemplateCategory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WidgetTemplateCategory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
