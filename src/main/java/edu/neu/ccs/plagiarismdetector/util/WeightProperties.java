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

@Configuration
@RefreshScope
public class WeightProperties {

    @Value("${weight.strategy1_weight}")
    private Double strategy1Weight;
    @Value("${weight.strategy2_weight}")
    private Double strategy2Weight;

    /**
     * The train config for the moss module
     * @param mossTrainedConfig The configuration for the trained module
     */
    @Autowired
    public WeightProperties(MossTrainedConfig mossTrainedConfig) {
        this.mossTrainedConfig = mossTrainedConfig;
    }


    /**
     * The configuration file for moss training
     */
    @Configuration
    public static class MossTrainedConfig {
        @Value("${weight.moss_trained_config.strategy1_weight}")
        private Double strategy1Weight;
        @Value("${weight.moss_trained_config.strategy2_weight}")
        private Double strategy2Weight;

        public Double getStrategy1Weight() {
            return strategy1Weight;
        }

        public Double getStrategy2Weight() {
            return strategy2Weight;
        }

        @Override
        public String toString() {
            return "MossTrainedConfig{" +
                    "strategy1Weight=" + strategy1Weight +
                    ", strategy2Weight=" + strategy2Weight +
                    '}';
        }
    }

    private final WeightProperties.MossTrainedConfig mossTrainedConfig;

    /**
     *
     * @return the weight for strategy 1
     */
    public Double getStrategy1Weight() {
        return strategy1Weight;
    }

    /**
     *
     * @return The weight for strategy 2
     */
    public Double getStrategy2Weight() {
        return strategy2Weight;
    }

    /**
     *
     * @return The traonind config
     */
    public WeightProperties.MossTrainedConfig getMossTrainedConfig() {
        return mossTrainedConfig;
    }

    /**
     *
     * @return String representation of the config file
     */
    @Override
    public String toString() {
        return "WeightProperties{" +
                "strategy1Weight=" + strategy1Weight +
                ", strategy2Weight=" + strategy2Weight +
                ", mossTrainedConfig=" + mossTrainedConfig +
                '}';
    }
}


