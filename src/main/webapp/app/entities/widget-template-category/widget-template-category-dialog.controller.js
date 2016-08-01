(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .controller('WidgetTemplateCategoryDialogController', WidgetTemplateCategoryDialogController);

    WidgetTemplateCategoryDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'WidgetTemplateCategory'];

    function WidgetTemplateCategoryDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, WidgetTemplateCategory) {
        var vm = this;

        vm.widgetTemplateCategory = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.widgetTemplateCategory.id !== null) {
                WidgetTemplateCategory.update(vm.widgetTemplateCategory, onSaveSuccess, onSaveError);
            } else {
                WidgetTemplateCategory.save(vm.widgetTemplateCategory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('webDashCwApp:widgetTemplateCategoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, widgetTemplateCategory) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        widgetTemplateCategory.image = base64Data;
                        widgetTemplateCategory.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();
