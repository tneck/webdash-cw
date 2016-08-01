(function() {
    'use strict';
    angular
        .module('webDashCwApp')
        .factory('WidgetTemplate', WidgetTemplate);

    WidgetTemplate.$inject = ['$resource', 'DateUtils'];

    function WidgetTemplate ($resource, DateUtils) {
        var resourceUrl =  'api/widget-templates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateCreated = DateUtils.convertDateTimeFromServer(data.dateCreated);
                        data.dateLastModified = DateUtils.convertDateTimeFromServer(data.dateLastModified);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
