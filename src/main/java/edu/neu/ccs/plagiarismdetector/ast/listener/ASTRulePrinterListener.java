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

package edu.neu.ccs.plagiarismdetector.ast.listener;

import edu.neu.ccs.plagiarismdetector.ast.grammar.CBaseListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Arrays;
import java.util.List;

/**
 * This class extends the CBaseListener that defines all entry and exit
 * methods for parsing a tree. This class is used to format the Abstract Syntax Tree in a
 * print friendly manner.
 */
public class ASTRulePrinterListener extends CBaseListener {
    /**
     * AST rules list
     */
    private final List<String> ruleNames;

    /**
     * String Builder for generating the AST presentation
     */
    private final StringBuilder builder = new StringBuilder();

    /**
     * indentation counter for AST nodes
     */
    private int indentation;

    /**
     * initializer for the listener
     * @param parser for parsing the AST
     */
    public ASTRulePrinterListener(Parser parser) {
        this.ruleNames = Arrays.asList(parser.getRuleNames());
    }

    /**
     * @param ctx rule context for the rule
     *            used for traversing the AST using roles
     */
    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        for (int i = 0; i < indentation; i++) {
            builder.append("  ");
        }

        int ruleIndex = ctx.getRuleIndex();
        String ruleName;
        if (ruleIndex >= 0 && ruleIndex < ruleNames.size()) {
            ruleName = ruleNames.get(ruleIndex);
        } else {
            ruleName = Integer.toString(ruleIndex);
        }
        builder.append(ruleName).append("\n");
    }

    /**
     * @return String form of StringBuilder
     * Overridden function to convert the builder object to String
     */
    @Override
    public String toString() {
        return builder.toString();
    }

    /**
     * @return no. of indentations
     */
    public int getIndentation() {
        return indentation;
    }

    /**
     * @param indentation to be set
     */
    public void setIndentation(int indentation) {
        this.indentation = indentation;
    }
}