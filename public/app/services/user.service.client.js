(function () {
    angular
        .module("PlagiarismDetector")
        .factory("UserService", userService);

    function userService($http) {

        function findUserByUserID(id) {
            return $http.get("/api/user/" + id);
        }

        function findCurrentUser() {
            return $http.get("/api/user/current");
        }

        function login(user) {
            return $http.post("/api/login", user);
        }

        function register(user) {
            return $http.post('/api/register', user);
        }

        function deleteUserById(id) {
            return $http.delete('/api/user/' + id);
        }

        function findAllUsers() {
            return $http.get('/api/user');
        }

        function isLoggedIn() {
            return $http.get('/api/isLoggedIn');
        }

        function logout() {
            return $http.post('/api/logout');
        }

        return {
            "findUserByUserID": findUserByUserID,
            "register": register,
            "findAllUsers": findAllUsers,
            "deleteUserById": deleteUserById,
            "isLoggedIn": isLoggedIn,
            "logout": logout,
            "login": login,
            "findCurrentUser": findCurrentUser
        };
    }
})();