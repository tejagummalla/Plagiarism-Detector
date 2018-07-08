(function () {
    angular
        .module("PlagiarismDetector")
        .controller("StatisticsController", StatisticsController);

    function StatisticsController($stateParams, StatsService, $rootScope, $state) {
        let vm = this;
        $rootScope.currentRoute = $state.current.name;

        function init() {
            StatsService.countChecks()
                .success(function (num) {
                    vm.cases = num;
                });

            StatsService.listStats()
                .success(function (statListItems) {
                    vm.statsList = statListItems;
                });
        }

        init();
    }
})();