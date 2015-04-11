app.factory('viewfactory', function($resoure){
    return $resource('place url here for list of songs', {}, {
        query: { method: 'GET', isArray: true }
    })
})