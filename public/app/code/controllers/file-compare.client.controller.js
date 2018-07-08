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
        .controller("FileCompareController", fileCompareController);

    function fileCompareController($scope, $timeout, $interval, Upload, $state,
                                   $stateParams, $uibModal, $log, $document, $rootScope) {
        let vm = this;
        vm.userID = $stateParams['uid'];
        $rootScope.currentRoute = $state.current.name;

        vm.uploads1 = [];
        vm.uploads2 = [];

        vm.toggle = {};
        vm.toggle.switch = false;
        vm.compStrategy = "LCS";

        vm.isCollapsed1 = false;
        vm.isCollapsed2 = false;

        vm.showInfo1 = false;

        vm.displayInfo = function(){
            if(vm.showInfo1){
                vm.showInfo1 = false;
            }else{
                vm.showInfo1 = true;
            }

        }

        vm.showInfo2 = false;

        vm.displayInfo2 = function(){
           if(vm.showInfo2){
               vm.showInfo2 = false;
           }else{
               vm.showInfo2 = true;
           }

        }

        vm.showSlider = function() {
            vm.sliderVisible = vm.compStrategy === 'WEIGHTED' && vm.toggle.switch;
            $timeout(function () {
                $scope.$broadcast('rzSliderForceRender');
            });
        };

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
                            return value + "%"
                    }
                }
            }
        };

        $scope.fileSelectionHandler1 = function(files){
            fileSelectionHandler(files, 1);
        };

        $scope.fileSelectionHandler2 = function(files){
            fileSelectionHandler(files, 2);
        };

        let fileSelectionHandler = function (files, student) {
            let uploads = [];
            // cleanup
            vm.cancelAll(student);

            vm.error = "";

            vm.doc1 = "";
            vm.doc2 = "";

            for (let i = 0; i < files.length; i++) {
                let file = files[i];
                let fileName = file.name;
                let isValid = true;
                let reason = "";

                let extn = fileName.split(".").pop();

                if (extn !== "c") {
                    isValid = false;
                    reason = "Invalid file format";
                }

                uploads.push({
                    file: file,
                    id: new Date().getTime() + fileName, // fake id
                    file_name: fileName,
                    isValid: isValid,
                    reason: reason
                });
            }

            if (student === 1) {
                vm.uploads1 = uploads;
            } else {
                vm.uploads2 = uploads;
            }

        };

        vm.startUploadFor = function (i, uploads, studentName) {
            let upload = uploads[i];

            upload.owner = studentName;

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

        vm.completionFor = function (i, uploads) {
            return uploads[i].completion;
        };

        vm.uploadStartRequestedFor = function (i, uploads) {
            return uploads[i].start_requested;
        };

        vm.uploadStartedFor = function (i, uploads) {
            return uploads[i].started;
        };

        vm.uploadRunningFor = function (i, uploads) {
            return uploads[i].running;
        };

        vm.uploadCompletedFor = function (i, uploads) {
            return uploads[i].completed;
        };

        vm.cancelUploadFor = function (i, uploads) {
            let upload = uploads[i];
            $interval.cancel(upload.task);
            uploads.splice(i, 1);
        };

        let isValidForUpload = function (i, uploads) {
            return uploads[i].isValid;
        };

        let isStartable = function (i, uploads) {
            return isValidForUpload(i, uploads) && !vm.uploadStartRequestedFor(i, uploads);
        };

        vm.anyStartable = function (uploads) {
            for (let i = 0; i < uploads.length; i++) if (isStartable(i, uploads)) return true;
            return false;
        };

        vm.startAll = function (uploads, studentName) {
            vm.error = "";
            vm.result = "";
            vm.doc1 = "";
            vm.doc2 = "";
            for (let i = 0; i < uploads.length; i++) if (isStartable(i, uploads)) vm.startUploadFor(i, uploads, studentName);
        };

        vm.cancelAll = function (student) {
            vm.error = "";
            vm.result = "";
            vm.doc1 = "";
            vm.doc2 = "";
            if (student === 1) {
                vm.uploads1 = [];
            } else {
                vm.uploads2 = [];
            }
        };

        vm.submit = function () {
            if (vm.uploads1.length === 0) {
                vm.error = "Please upload Student 1 files!";
                return;
            }
            if (vm.uploads2.length === 0) {
                vm.error = "Please upload Student 2 files!";
                return;
            }

            let completedFiles1 = vm.uploads1.filter(obj => obj.completed);
            let completedFiles2 = vm.uploads2.filter(obj => obj.completed);

            if (completedFiles1.length === 0) {
                vm.error = "Please upload valid Student 1 files! (Hint: May be you forgot to press Start All!)";
                return;
            }
            if (completedFiles2.length === 0) {
                vm.error = "Please upload valid Student 2 files! (Hint: May be you forgot to press Start All!)";
                return;
            }

            let files1 = completedFiles1.map(obj => obj.file);
            let files2 = completedFiles2.map(obj => obj.file);

            let metadata1 = completedFiles1.map(obj => obj.owner);
            let metadata2 = completedFiles2.map(obj => obj.owner);

            let metadata = metadata1.concat(metadata2);

            if (new Set(metadata).size === 1) {
                vm.error = "Need two different students for comparison!";
                return;
            }

            Upload.upload({
                url: '/api/user/' + vm.userID + '/compare',
                data: {
                    files: files1.concat(files2),
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

})();