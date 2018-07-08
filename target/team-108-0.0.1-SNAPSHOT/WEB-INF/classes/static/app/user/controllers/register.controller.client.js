(function () {
    angular
        .module("PlagiarismDetector")
        .controller("RegisterController", RegisterController);

    function RegisterController($stateParams, UserService, $location) {
        var vm = this;
        vm.register = register;
        var usernameLength = 32;
        var minPassword = 6;
        var maxPassword = 32;

        function register(user) {
            if (!user || !user.username || !user.password) {
                vm.error = "Please Enter Username and password to register!"
            } else if (!checkUsernameAndPassword(user.username, user.password)) {

            } else if (!user.emailAddress) {
                vm.error = "Please provide an email address so that we can send you reports.:)"
            }
            else {
                UserService
                    .register(user)
                    .success(function (user) {
                        $location.url('/user/' + user.id);
                    })
            }
        }

        function checkUsernameAndPassword(username, password) {
            if (username.length > usernameLength) {
                vm.error = "The username exceeds the given limit of 1-32 characters."
                return false
            } else if (password.length < minPassword || password.length > maxPassword) {
                vm.error = "The password denies the given limit of 6-32 characters."
                return false
            }
            return true;
        }

    }
})();