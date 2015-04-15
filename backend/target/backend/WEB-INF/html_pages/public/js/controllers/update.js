app.controller('update', function($scope, songService, $http, $location){
    $scope.song = songService.getSong()
    console.log($scope.song)
    $scope.disable = 'disabled';
    
    $scope.save = function(){
        var song = {
            id: $scope.song.id,
            title: $scope.song.songTitle,
            lyrics: $scope.song.songLyrics,
            artist: $scope.song.artistName,
            album: $scope.song.albumTitle
        };
        console.log(song);
        
        $http({
            method: 'POST',
<<<<<<< HEAD
            url: "http://backend-andrewsstuff.rhcloud.com/admin/update/" + song.id,
=======
            url: "http://localhost:8080/admin/update/" + song.id,
>>>>>>> 77ce9e2e5fe22d556c69514c1569cd40e828deca
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
            transformRequest: function(obj) {
                var str = [];
                for(var p in obj){
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                }
                return str.join("&");
            },
            data: song
        }).success(function(data, err){
            $location.path("/viewall")
        }).error(function(data, err){
            if(data.error){
                $scope.errMessage = data.error;
            }else{
                $scope.errMessage = 'An unknown error occured during update request.';
            }
        });
    }
});