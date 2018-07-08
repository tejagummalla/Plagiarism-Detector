(function () {
    angular
        .module("PlagiarismDetector")
        .controller("MultiCompareController", multiCompareController)
        .controller("ModalInstanceCtrl", modalInstanceCtrl);

    function multiCompareController($scope, $timeout, $interval, Upload, $stateParams,
                                    $uibModal, $log, $document, $rootScope, $state) {
        let vm = this;
        vm.userID = $stateParams['uid'];
        $rootScope.currentRoute = $state.current.name;

        vm.uploads = [];

        vm.toggle = {};
        vm.toggle.switch = false;
        vm.compStrategy = "LCS";

        vm.isCollapsed = false;

        vm.showSlider = function() {
            vm.sliderVisible = vm.compStrategy === 'WEIGHTED' && vm.toggle.switch;
            $timeout(function () {
                $scope.$broadcast('rzSliderForceRender');
            });
        };
        vm.showInfo2 = false;

        vm.displayInfo2 = function(){
           if(vm.showInfo2){
               vm.showInfo2 = false;
           }else{
               vm.showInfo2 = true;
           }

        }
        vm.showInfo1 = false;

                vm.displayInfo = function(){
                    if(vm.showInfo1){
                        vm.showInfo1 = false;
                    }else{
                        vm.showInfo1 = true;
                    }

                }

        $scope.slider = {
            value: 50,
            options: {
                floor: 0,
                ceil: 100,
                translate: function (value, sliderId, label) {
                    switch (label) {
                        case 'model':
                            return '<b>LCS (' + value + '), NW (' + (100 - value) + ')</b>';
                        default:
                            return value + '%'
                    }
                }
            }
        };

        $scope.fileSelectionHandler = function (files) {
            // cleanup
            vm.cancelAll();
            vm.error = "";
            vm.doc1 = "";
            vm.doc2 = "";

            for (let i = 0; i < files.length; i++) {
                let file = files[i];
                let fileName = file.name;
                let isValid = true;
                let reason = "";

                let extn = fileName.split(".").pop();
                let splitPath = file.webkitRelativePath.split("/");

                if (extn !== "c") {
                    isValid = false;
                    reason = "Invalid file format";
                }

                if (splitPath.length < 3) {
                    isValid = false;
                    reason = "Invalid directory structure";
                }

                vm.uploads.push({
                    file: file,
                    id: new Date().getTime() + fileName, // fake id
                    file_name: fileName,
                    isValid: isValid,
                    reason: reason
                });

            }
        };

        vm.startUploadFor = function (i) {
            let upload = vm.uploads[i];

            let file = upload.file;
            let splitPath = file.webkitRelativePath.split("/");

            upload.owner = splitPath[1];

            function simulateUpload() {
                upload.completion += 50;
                if (upload.completion === 100) {
                    upload.running = false;
                    upload.completed = true;
                    vm[upload.id] = upload;
                    $interval.cancel(upload.task);
                }
            }

            function randomWait() {
                return Math.max(500, 1000 * Math.random());
            }

            if (!upload.started) {
                upload.start_requested = true;
                $timeout(function () {
                    upload.started = true;
                    upload.running = true;
                    upload.completion = 0;
                    upload.task = $interval(simulateUpload, randomWait());
                }, randomWait());
            } else {
                upload.running = true;
                upload.task = $interval(simulateUpload, randomWait());
            }
        };

        vm.completionFor = function (i) {
            return vm.uploads[i].completion;
        };

        vm.uploadStartRequestedFor = function (i) {
            return vm.uploads[i].start_requested;
        };

        vm.uploadStartedFor = function (i) {
            return vm.uploads[i].started;
        };

        vm.uploadRunningFor = function (i) {
            return vm.uploads[i].running;
        };

        vm.uploadCompletedFor = function (i) {
            return vm.uploads[i].completed;
        };

        vm.cancelUploadFor = function (i) {
            let upload = vm.uploads[i];
            $interval.cancel(upload.task);
            vm.uploads.splice(i, 1);
        };

        vm.isValidForUpload = function (i) {
            return vm.uploads[i].isValid;
        };

        let isStartable = function (i) {
            return vm.isValidForUpload(i) && !vm.uploadStartRequestedFor(i);
        };

        vm.anyStartable = function () {
            for (let i = 0; i < vm.uploads.length; i++) if (isStartable(i)) return true;
            return false;
        };

        vm.startAll = function () {
            vm.error = "";
            vm.result = "";
            vm.doc1 = "";
            vm.doc2 = "";
            for (let i = 0; i < vm.uploads.length; i++) if (isStartable(i)) vm.startUploadFor(i);
        };

        vm.cancelAll = function () {
            vm.error = "";
            vm.result = "";
            vm.doc1 = "";
            vm.doc2 = "";
            vm.uploads = [];
        };

        vm.submit = function () {
            if (vm.uploads && vm.uploads.length) {
                let completedFiles = vm.uploads.filter(obj => obj.completed);
                let files = completedFiles.map(obj => obj.file);
                let metadata = completedFiles.map(obj => obj.owner);

                if (new Set(metadata).size === 1) {
                    vm.error = "Need atleast two students for comparison!";
                    return;
                }
                if (completedFiles.length !== 0) {
                    Upload.upload({
                        url: '/api/user/' + vm.userID + '/compare',
                        data: {
                            files: files,
                            metadata: metadata,
                            strategy: vm.toggle.switch ? vm.compStrategy : "",
                            weight: vm.toggle.switch ? $scope.slider.value : -1.0
                        },
                        arrayKey: ''
                    }).then(function (response) {

                        vm.documents = response.data.comparisonBundle.studentRepoCodeMappingDataList;
                        vm.result = response.data.diffStatisticsList;

                        vm.lcs = vm.result.LCS ? vm.result.LCS.metricList : null;
                        vm.nw = vm.result.NW ? vm.result.NW.metricList : null;
                        vm.weighted = vm.result.WEIGHTED ? vm.result.WEIGHTED.metricList : null;
                        vm.moss = vm.result.MOSS_TRAINED ? vm.result.MOSS_TRAINED.metricList : null;
                        vm.rows = vm.lcs || vm.nw;
                    }, function (err) {
                        vm.error = err.data.Status;
                        console.log(err);
                    });
                } else {
                    vm.error = "Please upload valid files! (Hint: May be you forgot to press Start All!)"
                }
            } else {
                vm.error = "Please upload a folder!"
            }
        };

        vm.animationsEnabled = true;

        vm.open = function (size, student1Identifier, student2Identifier, method, vmContext, parentSelector) {
            let parentElem = parentSelector ?
                angular.element($document[0].querySelector('.modal-demo ' + parentSelector)) : undefined;
            let modalInstance = $uibModal.open({
                animation: vm.animationsEnabled,
                ariaLabelledBy: 'modal-title',
                ariaDescribedBy: 'modal-body',
                templateUrl: 'myModalContent.html',
                controller: 'ModalInstanceCtrl',
                controllerAs: 'model',
                size: size,
                appendTo: parentElem,
                resolve: {
                    identifier1: function () {
                        return student1Identifier;
                    },
                    identifier2: function () {
                        return student2Identifier;
                    },
                    method: function () {
                        return method;
                    },
                    vmContext: function () {
                        return vmContext;
                    }
                }
            })
        };

    }

    function modalInstanceCtrl($uibModalInstance, identifier1, identifier2, method, vmContext) {
        let vm = this;
        vm.strategy = method;

        displayDiff(identifier1, identifier2, method, vmContext);

        vm.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };

        function displayDiff(identifier1, identifier2, method, context) {
            if (method === 'LCS') {
                vm.method = vmContext.lcs;
            } else if (method === 'NW') {
                vm.method = vmContext.nw;
            }

            for (let i = 0; i < vm.method.length; i++) {
                if (vm.method[i].student1Identifier === identifier1 &&
                    vm.method[i].student2Identifier === identifier2) {
                    vm.matchedLines = vm.method[i].matchedSnippets
                }
            }

            for (let j = 0; j < context.documents.length; j++) {
                let doc = vmContext.documents[j];
                if (doc.reponame === identifier1) {
                    vm.doc1 = doc.code;
                } else if (doc.reponame === identifier2) {
                    vm.doc2 = doc.code;
                }
            }

            calculateMatchedLines()
        }

        function onlyUnique(value, index, self) {
            return self.indexOf(value) === index;
        }

        function calculateMatchedLines() {
            let doc1Lines = [];
            let doc2Lines = [];

            for (let i = 0; i < vm.matchedLines.length; i++) {
                doc1Lines.push(vm.matchedLines[i].matchedLine1);
                doc2Lines.push(vm.matchedLines[i].matchedLine2);
            }

            doc1Lines = doc1Lines.filter(onlyUnique);
            doc2Lines = doc2Lines.filter(onlyUnique);

            vm.doc1Lines = doc1Lines.join(",");
            vm.doc2Lines = doc2Lines.join(",");
        }
    }

})();