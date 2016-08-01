(function() {
    'use strict';

    angular
        .module('webDashCwApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('widget-template-category', {
            parent: 'entity',
            url: '/widget-template-category',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'webDashCwApp.widgetTemplateCategory.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/widget-template-category/widget-template-categories.html',
                    controller: 'WidgetTemplateCategoryController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('widgetTemplateCategory');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('widget-template-category-detail', {
            parent: 'entity',
            url: '/widget-template-category/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'webDashCwApp.widgetTemplateCategory.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/widget-template-category/widget-template-category-detail.html',
                    controller: 'WidgetTemplateCategoryDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('widgetTemplateCategory');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'WidgetTemplateCategory', function($stateParams, WidgetTemplateCategory) {
                    return WidgetTemplateCategory.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'widget-template-category',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('widget-template-category-detail.edit', {
            parent: 'widget-template-category-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/widget-template-category/widget-template-category-dialog.html',
                    controller: 'WidgetTemplateCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WidgetTemplateCategory', function(WidgetTemplateCategory) {
                            return WidgetTemplateCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('widget-template-category.new', {
            parent: 'widget-template-category',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/widget-template-category/widget-template-category-dialog.html',
                    controller: 'WidgetTemplateCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                image: null,
                                imageContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('widget-template-category', null, { reload: true });
                }, function() {
                    $state.go('widget-template-category');
                });
            }]
        })
        .state('widget-template-category.edit', {
            parent: 'widget-template-category',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/widget-template-category/widget-template-category-dialog.html',
                    controller: 'WidgetTemplateCategoryDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['WidgetTemplateCategory', function(WidgetTemplateCategory) {
                            return WidgetTemplateCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('widget-template-category', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('widget-template-category.delete', {
            parent: 'widget-template-category',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/widget-template-category/widget-template-category-delete-dialog.html',
                    controller: 'WidgetTemplateCategoryDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['WidgetTemplateCategory', function(WidgetTemplateCategory) {
                            return WidgetTemplateCategory.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('widget-template-category', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
