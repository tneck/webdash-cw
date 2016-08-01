(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .controller('WidgetTemplateDeleteController',WidgetTemplateDeleteController);

    WidgetTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'WidgetTemplate'];

    function WidgetTemplateDeleteController($uibModalInstance, entity, WidgetTemplate) {
        var vm = this;

        vm.widgetTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            WidgetTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
