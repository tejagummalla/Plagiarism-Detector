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
            if (user) {
                UserService.login(user)
                    .then(function (response) {
                        let loggedInUser = response.data;
                        $rootScope.user = response.data;
                        if (loggedInUser) {
                            $location.url('/user/' + loggedInUser.id + '/compare');
                        } else {
                            vm.error = 'User not found';
                        }
                    }, function (err) {
                        vm.error = err.data.message;
                        console.log(err);
                    });
            } else {
                vm.error = 'Please enter correct credentials';
            }
        }

    }
})();