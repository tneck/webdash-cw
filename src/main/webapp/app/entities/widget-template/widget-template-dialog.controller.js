(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .controller('WidgetTemplateDialogController', WidgetTemplateDialogController);

    WidgetTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'WidgetTemplate', 'User', 'WidgetTemplateCategory'];

    function WidgetTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, WidgetTemplate, User, WidgetTemplateCategory) {
        var vm = this;

        vm.widgetTemplate = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.users = User.query();
        vm.widgettemplatecategories = WidgetTemplateCategory.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.widgetTemplate.id !== null) {
                WidgetTemplate.update(vm.widgetTemplate, onSaveSuccess, onSaveError);
            } else {
                WidgetTemplate.save(vm.widgetTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('webDashCwApp:widgetTemplateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateCreated = false;
        vm.datePickerOpenStatus.dateLastModified = false;

        vm.setImagePreview = function ($file, widgetTemplate) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        widgetTemplate.imagePreview = base64Data;
                        widgetTemplate.imagePreviewContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
