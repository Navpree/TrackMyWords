var app = angular.module('trackmywords', ['ngRoute']);

app.service('LoadDialog', function(){7
    this.open = function(){
        var elem = document.getElementById('load-cover');
        if(elem){
            elem.style.visibility = 'visible';
        }
    };
    this.close = function(){
        var elem = document.getElementById('load-cover');
        if(elem){
            elem.style.visibility = 'hidden';
        }
    }
});

app.service('songService', function(){
    var songs = ''

    this.setSong = function(data){
        songs = data
        console.log('song ' + angular.toJson(data))
    }

    this.getSong = function(){
        return songs
    }
});