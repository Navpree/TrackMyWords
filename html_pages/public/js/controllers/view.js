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

    $scope.next = function(){
        $scope.currentPage++;
        $scope.noPrev = "";
        if($scope.currentPage == $scope.pages){
            $scope.noNext = 'disabled';
        }
        $scope.request();
    }

    $scope.previous = function(){
        $scope.currentPage--;
        $scope.noNext = "";
        if($scope.currentPage == 1){
            $scope.noPrev = "disabled";
        }
        $scope.request();
    }

    $scope.request = function(){
        $http.get('http://backend-andrewsstuff.rhcloud.com/admin/view/' + $scope.currentPage + '?sort=title').success(function(data, err){
            $scope.list = data.songs;
            $scope.pages = data.pages;
        })
    }

    $scope.noPrev = 'disabled';
    $scope.currentPage = 1;
    $scope.request();
});
