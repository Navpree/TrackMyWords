app.config(['$routeProvider', function($routeProvider) {
        $routeProvider
            // route for the home page
            .when('/index', {
                templateUrl : 'index.html'
            }).
            when('/add',{
                templateUrl : 'add.html'
            }).
            when('/viewall', {
                templateUrl : 'view.html'
            }).
            otherwise({
                redirectTo: '/index'
            });
}]);