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

package edu.neu.ccs.plagiarismdetector.cdt;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.c.GCCLanguage;
import org.eclipse.cdt.core.model.ILanguage;
import org.eclipse.cdt.core.parser.*;
import org.eclipse.core.runtime.CoreException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Service to deal with CDT based logic
 */
public class CDTService {

    /**
     * logger
     */
    private static final Logger LOG = Logger.getLogger("CDTService");

    /**
     * @param s inout string
     * @return list of relevant nodes
     */
    public List<String> getASTNodes(String s) {
        ASTVisitor visitor = null;
        IASTTranslationUnit translationUnit;
        try {
            translationUnit = getIASTTranslationUnit(s.toCharArray());
            visitor = getVisitor();
            // Adapt visitor with source code unit
            translationUnit.accept(visitor);
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
        return visitor instanceof CDTBasedVisitor ? ((CDTBasedVisitor) visitor).getNodes() : null;
    }

    /**
     * @return visitor for traversal
     */
    private ASTVisitor getVisitor() {
        return new CDTBasedVisitor();
    }

    /**
     * @param code to be parsed
     * @return a translation unit
     * @throws CoreException exception thrown
     */
    private IASTTranslationUnit getIASTTranslationUnit(char[] code) throws CoreException {
        FileContent fc = FileContent.create("", code);
        Map<String, String> macroDefinitions = new HashMap<>();
        String[] includeSearchPaths = new String[0];
        IScannerInfo si = new ScannerInfo(macroDefinitions, includeSearchPaths);
        IncludeFileContentProvider ifcp = IncludeFileContentProvider.getEmptyFilesProvider();
        int options = ILanguage.OPTION_IS_SOURCE_UNIT;
        IParserLogService log = new DefaultLogService();
        return GCCLanguage.getDefault().getASTTranslationUnit(fc, si, ifcp, null, options, log);
    }
}