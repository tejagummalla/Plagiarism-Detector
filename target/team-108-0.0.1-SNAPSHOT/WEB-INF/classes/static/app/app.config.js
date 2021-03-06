(function () {
    angular
        .module("PlagiarismDetector")
        .config(configuration)
        .run(setPageTitle);

    function configuration($stateProvider, $urlRouterProvider, $httpProvider) {
        $httpProvider.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';
        $httpProvider.defaults.headers.put['Content-Type'] = 'application/json;charset=utf-8';

        $urlRouterProvider.otherwise('/login');

        $stateProvider
            .state('login', {
                url: '/login',
                templateUrl: 'app/user/templates/login.view.client.html',
                controller: "LoginController",
                controllerAs: 'model',
                title: 'Login'
            })
            .state('register', {
                url: '/register',
                templateUrl: 'app/user/templates/register.view.client.html',
                controller: "RegisterController",
                controllerAs: 'model',
                title: 'Register'
            })
            .state('currentUser', {
                url: '/user',
                templateUrl: 'app/user/templates/profile.view.client.html',
                controller: "UserController",
                controllerAs: 'model',
                title: 'Profile',
                resolve: {
                    isLoggedIn: isLoggedIn
                }
            })
            .state('user', {
                url: '/user/:uid',
                templateUrl: 'app/user/templates/profile.view.client.html',
                controller: "UserController",
                controllerAs: 'model',
                title: 'Profile',
                resolve: {
                    isLoggedIn: isLoggedIn
                }
            })
            .state('health', {
                url: '/health',
                templateUrl: 'app/system/templates/health.view.client.html',
                controller: 'HealthCheckController',
                controllerAs: 'model',
                title: 'Health',
                resolve: {
                    isLoggedIn: isLoggedIn
                }
            })
            .state('dashboard', {
                url: '/dashboard',
                templateUrl: 'app/user/templates/dashboard.view.client.html',
                controller: "DashboardController",
                controllerAs: 'model',
                title: 'Admin Dashboard',
                resolve: {
                    isLoggedIn: isLoggedIn
                }
            })
            .state('statistics', {
                url: '/statistics',
                templateUrl: 'app/system/templates/statistics.view.client.html',
                controller: "StatisticsController",
                controllerAs: 'model',
                title: 'System Statistics',
                resolve: {
                    isLoggedIn: isLoggedIn
                }
            })
            .state('code-compare', {
                url: '/user/:uid/compare',
                templateUrl: 'app/code/templates/compare.view.client.html',
                controller: "CompareController",
                controllerAs: 'model',
                title: 'Code Compare',
                resolve: {
                    isLoggedIn: isLoggedIn
                }
            })
            .state('code-compare.multi', {
                url: '/multi',
                templateUrl: 'app/code/templates/multi-compare.view.client.html',
                controller: "MultiCompareController",
                controllerAs: 'model',
                title: 'Multi Code Compare',
                resolve: {
                    isLoggedIn: isLoggedIn
                }
            })
            .state('code-compare.file', {
                url: '/file',
                templateUrl: 'app/code/templates/file-compare.view.client.html',
                controller: "FileCompareController",
                controllerAs: 'model',
                title: 'File Code Compare',
                resolve: {
                    isLoggedIn: isLoggedIn
                }
            })
            .state('code-compare.folder', {
                url: '/folder',
                templateUrl: 'app/code/templates/folder-compare.view.client.html',
                controller: "FolderCompareController",
                controllerAs: 'model',
                title: 'Folder Code Compare',
                resolve: {
                    isLoggedIn: isLoggedIn
                }
            });

        function isLoggedIn(UserService, $location) {
            UserService
                .isLoggedIn()
                .then(function (response) {
                    if(!response.data)
                        $location.url("/login");
                }, function (err) {
                    console.log(err);
                });
        }

    }

    function setPageTitle($rootScope) {
        $rootScope.$on('$routeChangeSuccess', function (event, current) {
            $rootScope.title = current.$$route.title;
        });
    }

})();