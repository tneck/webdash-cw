(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('widget-template', {
            parent: 'entity',
            url: '/widget-template?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'webDashCwApp.widgetTemplate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/widget-template/widget-templates.html',
                    controller: 'WidgetTemplateController',
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
                    $translatePartialLoader.addPart('widgetTemplate');
                    $translatePartialLoader.addPart('widgetAccess');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('widget-template-detail', {
            parent: 'entity',
            url: '/widget-template/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'webDashCwApp.widgetTemplate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/widget-template/widget-template-detail.html',
                    controller: 'WidgetTemplateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('widgetTemplate');
                    $translatePartialLoader.addPart('widgetAccess');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WidgetTemplate', function($stateParams, WidgetTemplate) {
                    return WidgetTemplate.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'widget-template',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('widget-template-detail.edit', {
            parent: 'widget-template-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/widget-template/widget-template-dialog.html',
                    controller: 'WidgetTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WidgetTemplate', function(WidgetTemplate) {
                            return WidgetTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('widget-template.new', {
            parent: 'widget-template',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/widget-template/widget-template-dialog.html',
                    controller: 'WidgetTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                dateCreated: null,
                                dateLastModified: null,
                                currentVersion: null,
                                access: null,
                                shareCode: null,
                                imagePreview: null,
                                imagePreviewContentType: null,
                                options: null,
                                contentUrl: null,
                                dataLibraries: null,
                                dataHtml: null,
                                dataCss: null,
                                dataJavascript: null,
                                dataInputVariables: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('widget-template', null, { reload: true });
                }, function() {
                    $state.go('widget-template');
                });
            }]
        })
        .state('widget-template.edit', {
            parent: 'widget-template',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/widget-template/widget-template-dialog.html',
                    controller: 'WidgetTemplateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WidgetTemplate', function(WidgetTemplate) {
                            return WidgetTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('widget-template', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('widget-template.delete', {
            parent: 'widget-template',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/widget-template/widget-template-delete-dialog.html',
                    controller: 'WidgetTemplateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WidgetTemplate', function(WidgetTemplate) {
                            return WidgetTemplate.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('widget-template', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
