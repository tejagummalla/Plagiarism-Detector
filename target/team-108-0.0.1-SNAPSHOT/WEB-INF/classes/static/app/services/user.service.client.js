(function () {
    angular
        .module("PlagiarismDetector")
        .factory("UserService", userService);

    function userService($http) {

        function findUserByUserID(id) {
            return $http.get("/api/user?id=" + id);
        }

        function getUserByCredentials(username, password) {
            return $http.get('/login' + "?username=" + username + "&password=" + password)
        }

        function register(user) {
            return $http.post('/api/register', user)
        }

        function deleteUserById(user) {
            return $http.post('/api/delete', user)
        }

        function findAllUsers() {
            return $http.get('/api/users');
        }

        function isLoggedIn() {
            return $http.get('/api/isLoggedIn');
        }

        function logout() {
            return $http.post('/api/logout');
        }

        return {
            "findUserByUserID": findUserByUserID,
            "getUserByCredentials": getUserByCredentials,
            "register": register,
            "findAllUsers": findAllUsers,
            "deleteUserById": deleteUserById,
            "isLoggedIn": isLoggedIn,
            "logout": logout
        };
    }
})();