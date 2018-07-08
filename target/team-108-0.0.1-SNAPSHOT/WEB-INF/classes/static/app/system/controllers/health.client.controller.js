(function () {
    angular
        .module("PlagiarismDetector")
        .controller("HealthCheckController", healthCheckController);

    function healthCheckController(HealthCheckService) {
        var vm = this;
        HealthCheckService
            .getHealth()
            .then(function (value) {
                vm.health = value.data.details;
            })
    }
})();
