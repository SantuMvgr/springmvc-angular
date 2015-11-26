describe('AccountSearchCtrl', function(){

    var $controllerConstructor, scope;

    beforeEach(module('ngBoilerPlate.account'));
    beforeEach(inject(function($controller, $rootScope){
        $controllerConstructor = $controller;
        scope = $rootScope.$new();
    }));

    it('should set scope accounts to the passed accounts object', function() {
        var mockAccounts = {};
        $controllerConstructor("AccountSearchCtrl", {"$scope":scope, accounts:mockAccounts});
        expect(scope.accounts).toBe(mockAccounts);
    });
});