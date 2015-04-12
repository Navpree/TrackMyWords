app.config(['$routeProvider', function($routeProvider) {
        $routeProvider
            // route for the home page
            .when('/index', {
                templateUrl : 'index.html',
                controller: 'index'
            }).
            when('/add',{
                templateUrl : 'add.html'
            }).
            when('/viewall', {
                templateUrl : 'view.html',
                controller: 'view'
            }).
            when('/update', {
                templateUrl : 'update.html'
            }).
            otherwise({
                redirectTo: '/viewall'
            });
}]);