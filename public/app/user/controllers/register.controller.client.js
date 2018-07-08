(function () {
    angular
        .module("PlagiarismDetector")
        .controller("RegisterController", RegisterController);

    function RegisterController($stateParams, UserService, $location, $rootScope) {
        let vm = this;
        vm.register = register;

        function register(user) {
            if (user) {
                UserService.register(user)
                    .then(function (response) {
                        let user = response.data;
                        $rootScope.user = response.data;

                        $location.url('/user/' + user.id + '/compare');
                    }, function (error) {
                        vm.error = error.data;
                        console.log(vm.error);
                    });
            }
            else {
                vm.error = 'Please enter all the details';
            }
        }

    }
})();