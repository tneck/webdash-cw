(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dashboard', {
            parent: 'entity',
            url: '/dashboard?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'webDashCwApp.dashboard.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dashboard/dashboards.html',
                    controller: 'DashboardController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dashboard');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('dashboard-detail', {
            parent: 'entity',
            url: '/dashboard/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'webDashCwApp.dashboard.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/dashboard/dashboard-detail.html',
                    controller: 'DashboardDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dashboard');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Dashboard', function($stateParams, Dashboard) {
                    return Dashboard.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'dashboard',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('dashboard-detail.edit', {
            parent: 'dashboard-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dashboard/dashboard-dialog.html',
                    controller: 'DashboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dashboard', function(Dashboard) {
                            return Dashboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dashboard.new', {
            parent: 'dashboard',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dashboard/dashboard-dialog.html',
                    controller: 'DashboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                options: null,
                                data1: null,
                                data2: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('dashboard', null, { reload: true });
                }, function() {
                    $state.go('dashboard');
                });
            }]
        })
        .state('dashboard.edit', {
            parent: 'dashboard',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dashboard/dashboard-dialog.html',
                    controller: 'DashboardDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Dashboard', function(Dashboard) {
                            return Dashboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dashboard', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('dashboard.delete', {
            parent: 'dashboard',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/dashboard/dashboard-delete-dialog.html',
                    controller: 'DashboardDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Dashboard', function(Dashboard) {
                            return Dashboard.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('dashboard', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
