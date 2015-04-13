app.config(['$routeProvider', function($routeProvider) {
        $routeProvider
            // route for the home page
            .when('/index', {
                templateUrl : 'index.html',
                controller: 'index'
            }).
            when('/add',{
                templateUrl : 'add.html',
                controller: 'insert'
            }).
            when('/viewall', {
                templateUrl : 'view.html',
                controller: 'view'
            }).
            when('/update', {
                templateUrl : 'update.html',
                controller: 'update'
            }).
            otherwise({
                redirectTo: '/viewall'
            });
}]);