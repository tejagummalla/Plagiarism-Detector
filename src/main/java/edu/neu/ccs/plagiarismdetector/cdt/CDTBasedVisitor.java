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
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * a visitor for CDT based tree
 */
public class CDTBasedVisitor extends ASTVisitor {
    /**
     * list of nodes traversed
     */
    private List<String> nodes;

    /**
     * instantiation
     */
    CDTBasedVisitor() {
        super();
        this.shouldVisitDeclarations = true;
        this.shouldVisitExpressions = true;

        this.nodes = new ArrayList<>();
    }

    /**
     * @param expression in the tree
     * @return process continue flag
     */
    @Override
    public int visit(IASTExpression expression) {
        nodes.add(expression.getRawSignature());
        return PROCESS_CONTINUE;
    }

    /**
     * @param declaration in the tree
     * @return process continue flag
     */
    @Override
    public int visit(IASTDeclaration declaration) {
        nodes.add(declaration.getRawSignature());
        return PROCESS_CONTINUE;
    }

    /**
     * @return list of nodes
     */
    public List<String> getNodes() {
        return nodes;
    }

}
