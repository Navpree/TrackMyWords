app.controller('update', function($scope, songService, $http, $location, LoadDialog){
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
        
        LoadDialog.open();
        $http({
            method: 'POST',
            url: "http://backend-andrewsstuff.rhcloud.com/admin/update/" + song.id,
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
            LoadDialog.close();
            $location.path("/viewall")
        }).error(function(data, err){
            LoadDialog.close();
            if(data.error){
                $scope.errMessage = data.error;
            }else{
                $scope.errMessage = 'An unknown error occured during update request.';
            }
        });
    }
});