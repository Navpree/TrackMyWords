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
            draft: $scope.song.draft
        }
        
        $http.post("https://backend-andrewsstuff.rhcloud.com/admin/insert", $scope.song).success(function(data, err){
            console.log("success " + err)
        }).error(function(data, err){
            console.log("error: " + data.error)
        })
    }
});