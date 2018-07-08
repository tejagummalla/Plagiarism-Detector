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

package edu.neu.ccs.plagiarismdetector.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * The properties file reffered to train the MOSS module
 */
@Configuration
@RefreshScope
public class TrainingProperties {

    @Autowired
    public TrainingProperties(MossConfig mossConfig) {
        this.mossConfig = mossConfig;
    }

    /**
     * Configuration of the moss bundle neede to hit the serverr
     */
    @Configuration
    public static class MossConfig {
        @Value("${training.moss_config.language:c}")
        private String language;
        @Value("${training.moss_config.user_ID:747158892}")
        private String userID;

        public String getLanguage() {
            return language;
        }

        public String getUserID() {
            return userID;
        }

        @Override
        public String toString() {
            return "MossConfig{" +
                    "language='" + language + '\'' +
                    ", userID='" + userID + '\'' +
                    '}';
        }
    }

    @Value("${training.initial_weight:50}")
    private Double initialWeight;
    @Value("${training.delta:1}")
    private Double delta;
    @Value("${training.max_iterations:20}")
    private int maxIterations;
    @Value("${training.adjust_value:0.01}")
    private Double adjustValue;

    private final MossConfig mossConfig;

    public Double getInitialWeight() {
        return initialWeight;
    }

    public Double getDelta() {
        return delta;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public Double getAdjustValue() {
        return adjustValue;
    }

    /**
     *
     * @return the string format of the properties configuration
     */
    @Override
    public String toString() {
        return "TrainingProperties{" +
                "initialWeight=" + initialWeight +
                ", delta=" + delta +
                ", maxIterations=" + maxIterations +
                ", adjustValue=" + adjustValue +
                ", mossConfig=" + mossConfig +
                '}';
    }

    public MossConfig getMossConfig() {
        return mossConfig;
    }

}

