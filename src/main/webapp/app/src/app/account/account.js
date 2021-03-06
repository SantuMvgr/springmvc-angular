 angular.module('ngBoilerPlate.account',['ui.router', 'ngResource'])
    .config(function($stateProvider) {
        $stateProvider.state('login', {
            url: '/login',
            views: {
                'main': {
                    templateUrl: 'account/login.tpl.html',
                    controller: 'LoginCtrl'
                }
            },
            data: {pageTitle: "Login"}
        })
         .state('register', {
            url: '/register',
            views: {
                'main': {
                    templateUrl: 'account/register.tpl.html',
                    controller: 'RegisterCtrl'
                }
            },
            data: {pageTitle: "Register"}
        })
            .state('accountSearch', {
                url: '/accounts/search',
                views: {
                    'main': {
                        templateUrl: 'account/search.tpl.html',
                        controller: 'AccountSearchCtrl'
                    }
                },
                data: {pageTitle: "Search Accounts"},
                resolve: {
                    accounts: function(accountService) {
                        return accountService.getAllAccounts();
                    }
                }
            });
    })
    .factory('sessionService', function() {
        var session = {};
        session.login = function(data) {
            localStorage.setItem("session", data);
            alert('User logged in with credentials ' + data.name + ' ' + data.password);
        };
        session.logout = function() {
            localStorage.removeItem("session");
        };
        session.isLoggedIn = function() {
            return localStorage.getItem("session") != null;
        };
        return session;
    })
    .factory('blogService', function($resource){
        var service = {};
        return service;
    })
    .factory('accountService', function($resource) {
        var service = {};
        service.register = function(account, success, failure) {
            var Account = $resource("/springmvc-angular/rest/accounts");
            Account.save({}, account, success, failure);
        };
        service.getAccountById = function(accountId) {
            var Account = $resource("/springmvc-angular/rest/accounts/:paramAccountId");
            return Account.get({paramAccountId:accountId}).$promise;
        },
        service.userExists = function(account, success, failure) {
            var Account = $resource("/springmvc-angular/rest/accounts");
            var data = Account.get({name:account.name}, function() {
                var accounts = data.accounts;
                if(accounts.length !== 0) {
                    success(accounts[0]);
                }else {
                    failure();
                }
        },
        failure);
        };
        service.getAllAccounts = function() {
            var Account = $resource("/springmvc-angular/rest/accounts");
            return Account.get().$promise.then(function(data) {
                return data.accounts;
            });
        };
        return service;
    })
    .controller('LoginCtrl', function($scope, sessionService, $state, accountService) {
        $scope.login = function() {
            accountService.userExists($scope.account, function(account){
                sessionService.login($scope.account);
                $state.go("home");
            }, function() {
                alert("This is not a registered login!");
            });


        };
    })
    .controller('RegisterCtrl', function($scope, sessionService, $state, accountService) {
        $scope.register = function() {
            accountService.register($scope.account,
            function(returnedData) {
                sessionService.login(returnedData);
                $state.go("home");
            },
            function() {
                alert("Error registering user");
            });

        };
    })
    .controller('AccountSearchCtrl', function($scope, accounts) {
        $scope.accounts = accounts;
    });