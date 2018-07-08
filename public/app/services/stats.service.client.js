(function () {
    angular
        .module("PlagiarismDetector")
        .factory("StatsService", StatsService);

    function StatsService($http) {
        function countChecks() {
            return $http.get("/api/stats/count");
        }

        function listStats() {
            return $http.get("/api/stats")
        }

        return {
            "countChecks": countChecks,
            "listStats": listStats
        }
    }

})();