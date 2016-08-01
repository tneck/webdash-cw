(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .controller('DashboardDetailController', DashboardDetailController);

    DashboardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Dashboard', 'User'];

    function DashboardDetailController($scope, $rootScope, $stateParams, previousState, entity, Dashboard, User) {
        var vm = this;

        vm.dashboard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('webDashCwApp:dashboardUpdate', function(event, result) {
            vm.dashboard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
