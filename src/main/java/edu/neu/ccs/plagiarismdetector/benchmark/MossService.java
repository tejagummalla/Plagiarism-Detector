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

package edu.neu.ccs.plagiarismdetector.benchmark;

import edu.neu.ccs.plagiarismdetector.util.LogService;
import edu.neu.ccs.plagiarismdetector.util.TrainingProperties;
import it.zielke.moji.SocketClient;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.Collection;

/**
 * Class to generate plagiarism report using Stanford Moss and
 * scraping the html to get a comparision value as percentage of
 * match
 */
@Component
public class MossService {

    private final TrainingProperties trainingProperties;
    private final LogService logService;

    @Autowired
    public MossService(LogService logService, TrainingProperties trainingProperties) {
        this.logService = logService;
        this.trainingProperties = trainingProperties;
    }

    /**
     * @param path of the code files
     * @return the percentage match between files at the given path
     */
    double getMatch(String path) {
        TrainingProperties.MossConfig mossConfig = trainingProperties.getMossConfig();
        try {
            Collection<File> files = FileUtils.listFiles(new File(
                    path), new String[]{mossConfig.getLanguage()}, true);

            SocketClient socketClient = new SocketClient();
            socketClient.setUserID(mossConfig.getUserID());
            socketClient.setLanguage(mossConfig.getLanguage());
            socketClient.run();

            for (File f : files) {
                socketClient.uploadFile(f);
            }

            socketClient.sendQuery();
            URL results = socketClient.getResultURL();
            String url;
            url = results.toString();
            logService.writeInfo("URL is:" + url);
            String html = Jsoup.connect(url).get().html();
            Document doc = Jsoup.parse(html, "utf-8");
            Elements tableTag = doc.getElementsByTag("table");
            Element tr = tableTag.select("tr").get(1);
            Element td = tr.select("td").get(0);
            String col = td.text();
            int i = col.indexOf('%');
            String percentStr = col.substring(i - 2, i);
            logService.writeInfo(percentStr);
            logService.writeInfo("Percent Match is:" + percentStr);
            return Double.parseDouble(percentStr);
        } catch (Exception e) {
            logService.writeError("suledisha@gmail.com", e.getMessage());
        }
        return -1;
    }
}