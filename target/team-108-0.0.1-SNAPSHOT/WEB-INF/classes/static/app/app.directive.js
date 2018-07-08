(function () {
    angular.module('PlagiarismDetector')
        .directive('prism', [function () {
                return {
                    restrict: 'A',
                    link: function ($scope, element, attrs) {
                        element.ready(function () {
                            Prism.highlightElement(element[0]);
                        });
                    }
                }
            }]
        );
})();
