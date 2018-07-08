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

package edu.neu.ccs.plagiarismdetector.comparison;

import edu.neu.ccs.plagiarismdetector.comparison.factory.ComparisonStrategyTypeFactory;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.ComparisonStrategy;
import edu.neu.ccs.plagiarismdetector.comparison.strategy.WeightedPolynomialStrategy;
import edu.neu.ccs.plagiarismdetector.report.DiffStatistics;
import edu.neu.ccs.plagiarismdetector.statistics.Stats;
import edu.neu.ccs.plagiarismdetector.statistics.StatsService;
import edu.neu.ccs.plagiarismdetector.user.User;
import edu.neu.ccs.plagiarismdetector.user.UserRepository;
import edu.neu.ccs.plagiarismdetector.util.WeightProperties;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static edu.neu.ccs.plagiarismdetector.comparison.ComparisonType.*;

/**
 * Service for comparison of the source codes
 */
@Component
public class ComparisonService {
    /**
     * logger
     */
    private static final Logger LOG = Logger.getLogger("ComparisonService");

    private final StatsService statsService;
    private final WeightProperties weightProperties;
    private final ComparisonStrategyTypeFactory comparisonStrategyTypeFactory;

    private final UserRepository userRepository;

    @Autowired
    public ComparisonService(StatsService statsService,
                             ComparisonStrategyTypeFactory comparisonStrategyTypeFactory,
                             WeightProperties weightProperties, UserRepository userRepository) {
        this.statsService = statsService;
        this.comparisonStrategyTypeFactory = comparisonStrategyTypeFactory;
        this.weightProperties = weightProperties;
        this.userRepository = userRepository;
    }

    /**
     * Applies comparison strategies one by one and returns a cumulative output
     * and stores the statistics
     *
     * @param userID             user who is running the comparison
     * @param uploadFiles        files uploaded
     * @param metadata           directory info for files
     * @param comparisonStrategy on demand comp. strategy
     * @return A result containing the statistics of all strategy runs and
     * the concatenated files of each user
     */
    public ComparisonResult compare(Long userID, MultipartFile[] uploadFiles, String[] metadata,
                                    String comparisonStrategy, Double weight) {
        Long startTime = System.nanoTime();

        ComparisonResult result = compare(uploadFiles, metadata, comparisonStrategy, weight);

        Long totalTime = System.nanoTime() - startTime;
        Stats stat = new Stats(totalTime);

        Optional<User> savedUser = userRepository.findById(userID);
        User actualUser = savedUser.orElse(null);

        stat.setUser(actualUser);

        statsService.saveStat(stat);

        return result;
    }

    /**
     * Applies comparison strategies one by one and returns a cumulative output
     *
     * @param uploadFiles files uploaded
     * @param metadata    directory info for files
     * @return A result containing the statistics of all strategy runs and
     * the concatenated files of each user
     */
    public ComparisonResult compare(MultipartFile[] uploadFiles, String[] metadata) {
        return compare(uploadFiles, metadata, null, -1.0);
    }

    /**
     * Applies comparison strategies one by one and returns a cumulative output
     *
     * @param uploadFiles        files uploaded
     * @param metadata           directory info for files
     * @param comparisonStrategy on demand comp. strategy
     * @param weight             assigned weight to strategy 1 from slider on UI
     * @return A result containing the statistics of all strategy runs and
     * the concatenated files of each user
     */
    public ComparisonResult compare(MultipartFile[] uploadFiles, String[] metadata,
                                    String comparisonStrategy, Double weight) {
        /*
        create a comparison bundle using input data i.e.
        basically a mapping of students with their codes
        */
        ComparisonBundle comparisonBundle = createComparisonBundle(uploadFiles, metadata);

        // a mapping of comparison strategy and corresponding similarity value
        Map<ComparisonType, DiffStatistics> diffStatisticsList = new EnumMap<>(ComparisonType.class);

        // Iterating over the types to get similarity values one by one
        for (ComparisonType type : getComparisonStratagies(comparisonStrategy)) {
            ComparisonStrategy strategy = comparisonStrategyTypeFactory.getComparisonStrategy(type);

            if (strategy instanceof WeightedPolynomialStrategy) {
                if (weight != -1) {
                    ((WeightedPolynomialStrategy) strategy).getTypeWeightsMapping().put(LCS, weight);
                    ((WeightedPolynomialStrategy) strategy).getTypeWeightsMapping().put(NW, 100.0 - weight);
                } else {
                    ((WeightedPolynomialStrategy) strategy).
                            getTypeWeightsMapping().
                            put(LCS, weightProperties.getStrategy1Weight());
                    ((WeightedPolynomialStrategy) strategy).
                            getTypeWeightsMapping().
                            put(NW, weightProperties.getStrategy2Weight());
                }
            }

            ComparisonContext comparisonContext = new ComparisonContext(strategy);
            diffStatisticsList.put(type, comparisonContext.compare(comparisonBundle, diffStatisticsList));
        }

        return new ComparisonResult(comparisonBundle, diffStatisticsList);
    }


    /**
     * To get the comparison strategy based on what the user has selected.
     * @param comparisonStrategy The strategy of comparison that we need to check
     * @return The filtered file for the comparison strategy
     */
    private List<ComparisonType> getComparisonStratagies(String comparisonStrategy) {
        if (StringUtils.isEmpty(comparisonStrategy))
            return Arrays.asList(ComparisonType.values());

        ComparisonType compType = ComparisonType.valueOf(comparisonStrategy);

        switch (compType) {
            case LCS:
                return stream()
                        .filter(c -> c.equals(LCS))
                        .collect(Collectors.toList());
            case NW:
                return stream()
                        .filter(c -> c.equals(NW))
                        .collect(Collectors.toList());
            case WEIGHTED:
                return stream()
                        .filter(c -> !c.equals(MOSS_TRAINED))
                        .collect(Collectors.toList());
            case MOSS_TRAINED:
                return stream()
                        .filter(c -> !c.equals(WEIGHTED))
                        .collect(Collectors.toList());
            default:
                return Arrays.asList(ComparisonType.values());
        }

    }

    /**
     * Get the comparison package of the files that needs to be compared
     * @param uploadFiles files uploaded
     * @param metadata    directory info for files
     * @return a bundle with mapping of repo and corresponding code
     */
    public ComparisonBundle createComparisonBundle(MultipartFile[] uploadFiles, String[] metadata) {
        Map<String, List<String>> studentCodeMapping = null;
        int i = 0;

        for (MultipartFile multipartFile : uploadFiles) {
            try {
                String content = new String(multipartFile.getBytes());
                String studentRepo = metadata[i];
                if (studentCodeMapping == null) {  // why o why !!!
                    studentCodeMapping = new HashMap<>();
                }
                List<String> codesList = studentCodeMapping.get(studentRepo); // false negative
                if (CollectionUtils.isEmpty(codesList)) {
                    codesList = new ArrayList<>();
                    studentCodeMapping.put(studentRepo, codesList);
                }
                codesList.add(content);
                i++;
            } catch (IOException e) {
                LOG.warning(e.getMessage());
            }
        }

        List<StudentRepoCodeMappingData> list = new ArrayList<>();

        if (studentCodeMapping != null) {
            studentCodeMapping.forEach((repoName, codesList) -> {
                StudentRepoCodeMappingData studentRepoCodeMappingData = new StudentRepoCodeMappingData(repoName,
                        StringUtils.join(codesList, System.lineSeparator()));
                list.add(studentRepoCodeMappingData);
            });
        }

        return new ComparisonBundle(list);
    }
}
