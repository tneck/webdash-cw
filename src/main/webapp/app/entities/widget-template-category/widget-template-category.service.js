(function() {
    'use strict';
    angular
        .module('webDashCwApp')
        .factory('WidgetTemplateCategory', WidgetTemplateCategory);

    WidgetTemplateCategory.$inject = ['$resource'];

    function WidgetTemplateCategory ($resource) {
        var resourceUrl =  'api/widget-template-categories/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
