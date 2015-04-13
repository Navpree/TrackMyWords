app.controller('insert', function($scope, $http){
    $scope.song = []
    
    $scope.create = function(){
        console.log($scope.song)
        var song = {
            title: $scope.song.title,
            artist: $scope.song.artist,
            lyrics: $scope.song.lyrics,
            release_date: $scope.song.date,
            album: $scope.song.album,
            draft: $scope.song.draft,
        }
        
        $http({
            method: 'POST',
            url: "http://backend-andrewsstuff.rhcloud.com/admin/insert",
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
            console.log("success " + err);
            $location.path('/viewall');
        }).error(function(data, err){
            console.log("error: " + data.error);
            $location.path('/viewall');
        });
    }
});