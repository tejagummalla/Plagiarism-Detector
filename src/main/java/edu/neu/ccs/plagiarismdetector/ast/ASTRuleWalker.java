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

package edu.neu.ccs.plagiarismdetector.ast;

import edu.neu.ccs.plagiarismdetector.ast.listener.ASTRulePrinterListener;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.*;

/**
 * a walker for traversing the AST to list rules
 */
class ASTRuleWalker extends ParseTreeWalker {
    static final ASTRuleWalker DEFAULT = new ASTRuleWalker();

    /**
     * To wlak , traverse the created ASt
     * @param listener    used while walking
     * @param t           Parse tree to be walked
     * @param indentation no. of indentations
     */
    void walk(ParseTreeListener listener, ParseTree t, int indentation) {
        if (t instanceof ErrorNode) {
            listener.visitErrorNode((ErrorNode) t);
            return;
        } else if (t instanceof TerminalNode) {
            listener.visitTerminal((TerminalNode) t);
            return;
        }

        RuleNode r = (RuleNode) t;
        RuleContext rtx = (RuleContext) t;

        boolean toBeIgnored = rtx.getChildCount() == 1
                && rtx.getChild(0) instanceof ParserRuleContext;

        ((ASTRulePrinterListener) listener).setIndentation(indentation);

        if (!toBeIgnored) {
            enterRule(listener, r);
        }
        for (int i = 0; i < rtx.getChildCount(); i++) {
            ParseTree element = rtx.getChild(i);
            if (element instanceof RuleContext) {
                walk(listener, element, indentation + (toBeIgnored ? 0 : 1));
            }
        }
        exitRule(listener, r);
    }

}
