describe('view.js Tests', function(){
    beforeEach(module('trackmywords'));
    
    var controller;
    var $scope = {};
    var $httpBackend;
    
    beforeEach(inject(function(_$controller_, _$httpBackend_, _$http_){
        $httpBackend = _$httpBackend_;
        $scope = {};
        controller = _$controller_('view', {$scope: $scope, $http: _$http_});
    }));
    
    it('Expect navigation links disabled', function(){
        $scope.pages = 2;
        $scope.currentPage = 1;
        $scope.next();
        expect($scope.noNext).toEqual('disabled');
        $scope.previous();
        expect($scope.noPrev).toEqual('disabled');
        expect($scope.noNext).toEqual('');
    });
    
    it('Expect get request', function(){
        $scope.currentPage = 1;
        $scope.request();
        $scope.remove(1);
        $httpBackend.expectGET('http://backend-andrewsstuff.rhcloud.com/admin/view/1?sort=title');
        $httpBackend.expectGET('http://backend-andrewsstuff.rhcloud.com/admin/delete/1');
    });
});

describe('insert.js Tests', function(){
    beforeEach(module('trackmywords'));
    
    var controller;
    var $scope = {};
    var $httpBackend;
    
    beforeEach(inject(function(_$controller_, _$httpBackend_, _$http_){
        $httpBackend = _$httpBackend_;
        $scope = {};
        controller = _$controller_('insert', {$scope: $scope, $http: _$http_});
    }));
    
    it('Expect post request', function(){
        $scope.create();
        $httpBackend.expectPOST('http://backend-andrewsstuff.rhcloud.com/admin/insert');
        expect($scope.song).toBeDefined();
    });
});

describe('update.js Tests', function(){
    beforeEach(module('trackmywords'));
    
    var controller;
    var $scope = {};
    var $httpBackend;
    
    beforeEach(inject(function(_$controller_, _$httpBackend_, _$http_){
        $httpBackend = _$httpBackend_;
        $scope = {};
        controller = _$controller_('update', {$scope: $scope, $http: _$http_});
    }));
    
    it('Expect post request', function(){
        $httpBackend.expectPOST('http://dddbackend-andrewsstuff.rhcloud.com/admin/update/');
        $scope.save();
        expect($scope.disable).toEqual('disabled');
        expect($scope.song).toBeUndefined();
    });
});