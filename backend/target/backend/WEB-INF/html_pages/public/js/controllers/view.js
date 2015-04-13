app.controller('view',function($scope, $location, $http, $route, songService, LoadDialog){
    $scope.query = '';
    
    $scope.remove = function(id){
        console.log(id)
        LoadDialog.open();
        $http.get('http://backend-andrewsstuff.rhcloud.com/admin/delete/'+id).success(function(data, err){
            LoadDialog.close();
            $route.reload();
        }).error(function(data,err){
            console.log("error: " + err)
            LoadDialog.close();
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
        $scope.list = [];
        $scope.pages = 0;
        var quer = '';
        if($scope.query && $scope.query !== ''){
            quer = '&title=' + $scope.query;
        }
        LoadDialog.open();
        $http.get('http://backend-andrewsstuff.rhcloud.com/admin/view/' + $scope.currentPage + '?sort=title' + quer).success(function(data, err){
            $scope.list = data.songs;
            $scope.pages = data.pages;
            LoadDialog.close();
        }).error(function(data,err){
            LoadDialog.close();
        });
    };

    $scope.noPrev = 'disabled';
    $scope.currentPage = 1;
    $scope.request();
});
