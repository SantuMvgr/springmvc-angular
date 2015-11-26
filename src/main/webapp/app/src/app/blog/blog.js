angular.module('ngBoilerPlate.blog', ['ui.router', 'ngBoilerPlate.account','ngBoilerPlate.blogEntry', 'ngResource', 'hateoas'])
    .config(function($stateProvider) {
        $stateProvider.state('manageBlogs', {
            url: '/manage/blogs?accountId',
            views: {
                'main': {
                    templateUrl: 'blog/manage-blogs.tpl.html',
                    controller: 'ManageBlogCtrl'
                }
            },
            resolve: {
                account: function(accountService, $stateParams) {
                    return accountService.getAccountById($stateParams.accountId);
                },
                blogs: function(blogService, $stateParams) {
                    return blogService.getBlogsForAccount($stateParams.accountId);
                }
            },
            data: {pageTitle: "Blogs"}
        });
    })
    .factory('blogService', function($resource, $q){
        var service = {};
        service.createBlog = function(accountId, blogData) {
            var Blog = $resource("/springmvc-angular/rest/accounts/:paramAccountId/blogs");
            return Blog.save({paramAccountId: accountId}, blogData).$promise;
        };
        service.getAllBlogs = function(accountId) {
            var Blog = $resource("/springmvc-angular/rest/blogs");
            return Blog.get().$promise.then(function(data){
                return data.blogs;
            });
        };
        service.getBlogsForAccount = function(accountId) {
            var deferred = $q.defer();
            var Account = $resource("/springmvc-angular/rest/accounts/:paramAccountId");
            Account.get({paramAccountId:accountId}, function(account) {
                var Blog = account.resource('blogs');
                Blog.get(null,
                    function(data){
                        deferred.resolve(data.blogs);
                    },
                    function() {
                        deferred.reject();
                    }
                );
            });
            return deferred.promise;
        };
        service.findAllBlogEntries = function(blogId) {
            var BlogEntry =$resource("/springmvc-angular/rest/blogs/:paramBlogId/blog-entries");
            return BlogEntry.get({paramBlogId:blogId}).$promise.then(function(data) {
                return data;
            });
        };
        service.createBlogEntry = function(blogId, blogEntryData) {
            var BlogEntry = $resource("/springmvc-angular/rest/blogs/:paramBlogId/blog-entries");
            return BlogEntry.save({paramBlogId: blogId}, blogEntryData).$promise;
        };
        return service;
    })
    .controller('ManageBlogCtrl', function($scope, blogs, account, blogService, $state){
        $scope.name = account.name;
        $scope.blogs = blogs;
        $scope.createBlog = function(blogName) {
            blogService.createBlog(account.rid, {
                title : blogName
            }).then(function() {
                $state.go("manageBlogs", { accountId : account.rid }, { reload : true });
            });
        };
    });