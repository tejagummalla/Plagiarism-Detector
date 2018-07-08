(function () {
    angular
        .module("PlagiarismDetector")
        .controller("LoginController", loginContoller);

    function loginContoller($stateParams, UserService, $location, $rootScope, StatsService) {
        let vm = this;
        vm.login = login;

        function init() {
            StatsService.countChecks()
                .success(function (num) {
                    vm.cases = num;
                })
                .error(function (err) {
                    console.log(err);
                })
        }

        init();

        function login(user) {
            if (!user) {
                vm.error = "Please Enter Username and Password";
            } else if (!user.username) {
                vm.error = "Please Enter your Username";
            } else if (!user.password) {
                vm.error = "Please Enter Your Password";
            } else {
                UserService
                    .getUserByCredentials(user.username, user.password)
                    .success(function (user) {
                        if (user) {
                            $rootScope.currentUser = user;
                            $location.url('/user/' + user.id)
                        } else {
                            vm.error = "User Id or Password incorrect!";
                        }
                    })
            }
        }
    }
})();