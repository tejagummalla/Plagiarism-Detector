(function () {
    angular
        .module("PlagiarismDetector")
        .factory("HealthCheckService", healthCheckService);

    function healthCheckService($http) {

        function getHealth(){
            return $http.get('/actuator/health')
        }

        return {
            "getHealth": getHealth
        };

    }
})();