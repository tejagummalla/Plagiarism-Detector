(function () {
    angular
        .module("PlagiarismDetector")
        .controller("HomeController", homeController);

    function homeController($stateParams, $location, UserService, $rootScope, $state) {
        let vm = this;
        $rootScope.currentRoute = $state.current.name;

        function init() {
            UserService.findCurrentUser()
                .then(function (response) {
                    vm.user = response.data;
                    vm.userID = vm.user.id;
                    $rootScope.user = response.data;
                }, function (error) {
                    vm.error = error.data.message;
                    console.log(vm.error);
                });
        }

        init();
    }
})();