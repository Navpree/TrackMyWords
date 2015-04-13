app.controller('update', function($scope, songService, $http, $location){
    $scope.song = songService.getSong()
    console.log($scope.song)
    
    
    $scope.save = function(){
        console.log($scope.song)
        $http.post("http://backend-andrewsstuff.rhcloud.com/admin/update/" , $scope.song).success(function(data, err){
            $location.path("/viewall")
        })
    }
});