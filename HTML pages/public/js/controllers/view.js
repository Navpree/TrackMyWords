app.service('songService', function(){
    var songs = ''
    
    this.setSong = function(data){
        
        songs = data
        console.log('song ' + angular.toJson(data))
    }
    
    this.getSong = function(){
        return songs
    }
})

app.controller('view',function($scope, $location, $http, $route, songService){
    $scope.remove = function(id){
        console.log(id)
        $http.get('http://backend-andrewsstuff.rhcloud.com/admin/delete/'+id).success(function(data, err){
            $route.reload();
        }).error(function(data,err){
            console.log("error: " + err)
        })
    }
    
    $scope.update = function(song){
        songService.setSong(song)
        $location.path('/update')
        console.log('get song' + songService.getSong())
        
        
    }
    
    $http.get('http://backend-andrewsstuff.rhcloud.com/admin/view/1?sort=title').success(function(data, err){
        $scope.list = data.songs
    })
});

