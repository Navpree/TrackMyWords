app.factory('viewfactory', function($resoure){
    return $resource('http://backend-andrewsstuff.rhcloud.com/admin/view/1?sort=title', {}, {
        query: { method: 'GET', isArray: true }
    })
})