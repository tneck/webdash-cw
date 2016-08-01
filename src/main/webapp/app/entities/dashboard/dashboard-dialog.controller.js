(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .controller('DashboardDialogController', DashboardDialogController);

    DashboardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Dashboard', 'User'];

    function DashboardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Dashboard, User) {
        var vm = this;

        vm.dashboard = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.dashboard.id !== null) {
                Dashboard.update(vm.dashboard, onSaveSuccess, onSaveError);
            } else {
                Dashboard.save(vm.dashboard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('webDashCwApp:dashboardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
