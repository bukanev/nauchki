angular.module('app', []).controller('indexController',

    function ($scope, $http) {

        const contextPath = 'https://nauchki.herokuapp.com';

        $scope.getUser = function () {
            $http.get(contextPath + "/getuser")
                .then(function (resp) {
                })
        };
        $scope.login = function () {
            $http.post(contextPath + '/login', $scope.user)
                .then(function () {
                    $scope.getUser()
                });
        };
    });