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
                templateUrl : 'view.html',
                controller: 'view'
            }).
            when('/update', {
                templateUrl : 'update.html'
            }).
            otherwise({
                redirectTo: '/index'
            });
}]);