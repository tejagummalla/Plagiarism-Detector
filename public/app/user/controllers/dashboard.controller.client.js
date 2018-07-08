(function () {
    angular
        .module("PlagiarismDetector")
        .controller("DashboardController", dashboardController);

    function dashboardController($stateParams, UserService, $location, $rootScope, $state) {
        let vm = this;
        vm.users = [];
        $rootScope.currentRoute = $state.current.name;

        function init() {
            UserService.findAllUsers()
                .success(function (users) {
                    users.forEach(function (user) {
                        if (!user.isAdmin) {
                            vm.users.push(user);
                        }
                    })
                });
        }

        init();

        vm.deleteUser = function (id, index) {
            UserService
                .deleteUserById(id)
                .success(function (status) {
                    if (status.code === 200) {
                        vm.users.splice(index, 1);
                        vm.message = status.message;

                    } else {
                        vm.error = status.message;
                    }
                }, function (err) {
                    vm.error = err.data.message;
                });
        }
    }
})();