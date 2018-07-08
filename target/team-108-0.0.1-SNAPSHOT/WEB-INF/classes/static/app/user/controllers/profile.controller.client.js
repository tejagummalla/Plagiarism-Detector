// why not ES6 :(

angular.module("PlagiarismDetector")
    .controller("UserController", userController);

function userController(UserService, $stateParams, $rootScope, $location) {
    var vm = this;
    vm.userID = $stateParams['uid'];

    vm.user = $rootScope.currentUser;

    UserService.findUserByUserID(vm.userID)
        .then(function (response) {
            vm.user = response.data;
            if (vm.user.username == "admin") {
                vm.dashboard = true;
            }
        });

    vm.logout = function() {
        UserService.
            logout()
            .then(function (response) {
                console.log(response);
                $location.url("/login");
            }, function(error) {
                console.log(error);
            })
    }

    function logout() {
        $rootScope.user = null;
        $location.url('/login')
    }
}