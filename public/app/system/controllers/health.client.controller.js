(function () {
    angular
        .module("PlagiarismDetector")
        .controller("HealthCheckController", healthCheckController);

    function healthCheckController(HealthCheckService, $rootScope, $state) {
        let vm = this;
        $rootScope.currentRoute = $state.current.name;

        HealthCheckService
            .getHealth()
            .then(function (value) {
                vm.health = value.data.details;
            });
    }

})();
