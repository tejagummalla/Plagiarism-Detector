/*
 * Copyright (c) 2018. Team-108 Inc. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License.  You may obtain a copy
 *  of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 */

(function () {
    angular
        .module("PlagiarismDetector")
        .controller("CompareController", compareController);

    function compareController($scope, $stateParams, $state, $rootScope) {
        let vm = this;
        vm.userID = $stateParams['uid'];
        vm.tabs = [];

        $rootScope.currentRoute = $state.current.name;

        vm.active = function (route) {
            return $state.is(route);
        };

        function init() {
            vm.tabs = [
                {heading: "Compare Multiple Folders", route: "app.code-compare.multi", active: false},
                {heading: "Compare Two Folders", route: "app.code-compare.folder", active: false},
                {heading: "Compare Two Files", route: "app.code-compare.file", active: false},
            ];
        }

        init();

        vm.go = function (route) {
            $state.go(route);
        };

        $scope.$on("$stateChangeSuccess", function () {
            vm.tabs.forEach(function (tab) {
                tab.active = vm.active(tab.route);
            });
        });

    }

})();