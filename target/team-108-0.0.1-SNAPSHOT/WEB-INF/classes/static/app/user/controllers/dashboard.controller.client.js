(function(){
    angular
        .module("PlagiarismDetector")
        .controller("DashboardController",dashboardController)

    function dashboardController($stateParams, UserService, $location){
        var vm  = this;
        vm.deleteUser = deleteUser;

        UserService
            .findAllUsers()
            .success(function(users){
                vm.users = users;
            })

        function deleteUser(user){
            UserService
                .deleteUserById(user)
                .success(function(status){
                    if (status.code == 200){
                        $location.url('/dashboard')
                        vm.message = status.message
                    }else{
                        vm.error = status.message
                    }
                })
        }
    }
})();