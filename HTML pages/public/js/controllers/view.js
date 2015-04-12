app.controller('view',function($scope, $location, $http, $route){
    $scope.remove = function(id){
        console.log(id)
        $http.get('http://backend-andrewsstuff.rhcloud.com/admin/delete/'+id).success(function(data, err){
            $route.reload();
        }).error(function(data,err){
            console.log("error: " + err)
        })
    }
    
    $scope.update = function(){
        $location.path('/update')
    }
    
    $http.get('http://backend-andrewsstuff.rhcloud.com/admin/view/1?sort=title').success(function(data, err){
        console.log(data)
        $scope.list = data.songs
    })
});