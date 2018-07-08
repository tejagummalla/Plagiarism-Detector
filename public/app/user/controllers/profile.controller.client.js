(function () {
    angular.module("PlagiarismDetector")
        .controller("UserController", userController);

    function userController(UserService, $stateParams, $rootScope, $location, $state) {
        let vm = this;
        vm.userID = $stateParams['uid'];
        $rootScope.currentRoute = $state.current.name;

        let init = function () {
            UserService.findCurrentUser()
                .then(function (response) {
                    vm.user = response.data;
                    vm.userID = vm.user.id;
                    $rootScope.user = response.data;
                }, function (error) {
                    vm.error = error.data.message;
                    console.log(vm.error);
                });
        };

        init();

        vm.logout = function () {
            UserService.logout()
                .then(function (response) {
                    $rootScope.user = null;
                    $location.url("/login");
                }, function (error) {
                    vm.error = error.data.message;
                    console.log(error);
                })
        };

    }
})();