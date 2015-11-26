/**
 * Created by Sandy on 10/10/2015.
 */
angular.module('ngBoilerPlate.blogEntry',['ui.router','ngBoilerPlate.blog', 'ngResource', 'hateoas'])
    .config(function($stateProvider){
        $stateProvider.state('getBlogEntries', {
            url: '/manage/blogs/{blogId}',
            views: {
                main: {
                    templateUrl: 'blogEntry/manage-blogEntries.tpl.html',
                    controller: 'BlogEntryController'
                }
            },
            resolve: {
                blogEntries: function(blogService, $stateParams) {
                    return blogService.findAllBlogEntries($stateParams.blogId);

                }

            },
            data: {pageTitle: "Blog"}
        });
    })
    .factory('blogEntryService', function($resource){
        var service = {};
        service.deleteBlogEntry = function(blogEntry){
            var blogEntrytoDelete = $resource("/springmvc-angular/rest/blog-entries/:blogEntryId");
            return blogEntrytoDelete.remove({blogEntryId:blogEntry.rid}).$promise.then(function(data){
                return data;
            });
        };
        return service;
    })
    .controller('BlogEntryController', function($scope, blogEntries, blogService, blogEntryService, $state, $stateParams) {
        $scope.blogEntries = blogEntries.entries;
        $scope.title = blogEntries.title;
        $scope.createBlogEntry = function(newBlogTitle, newBlogContent) {
            blogService.createBlogEntry($stateParams.blogId, {
                title: newBlogTitle,
                content: newBlogContent
            }).then(function(){
                $state.go("getBlogEntries", {blogId: $stateParams.blogId}, {reload:true});
            });
        };
        $scope.deleteBlogEntry = function(blogEntry) {
            blogEntryService.deleteBlogEntry(blogEntry).then(function(){
                $state.go("getBlogEntries", {blogId: $stateParams.blogId}, {reload:true});
            });
        };
    });