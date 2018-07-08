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

import edu.neu.ccs.plagiarismdetector.ast.grammar.CParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Utlity class for all things AST
 * parse string code input, create AST
 * represents an AST node
 */
public class AST {
    /**
     * The payload will either be the name of the parser rule, or the token
     * of a leaf in the tree.
     */
    private final Object payload;
    /**
     * list of AST nodes rules list
     */
    private static List<String> ruleNames;
    /**
     * All child nodes of this AST node.
     */
    private final List<AST> children;

    /**
     * @param tree parse tree representation of the code
     */
    public AST(ParseTree tree) {
        this(null, tree);
    }

    /**
     * @param ast root node of AST
     * @param tree parse tree to be created
     */
    public AST(AST ast, ParseTree tree) {
        this(ast, tree, new ArrayList<>());
    }

    /**
     * @param parent parent AST node
     * @param tree at the child of this AST node
     * @param children AST nodes
     */
    private AST(AST parent, ParseTree tree, List<AST> children) {
        this.payload = getPayload(tree);
        this.children = children;

        if (parent == null) {
            // We're at the root of the AST, traverse down the parse tree to fill
            // this AST with nodes.
            walk(tree, this);
        } else {
            parent.children.add(this);
        }
    }

    /**
     * Set rule names
     * @param ruleNames to be set
     */
    static void setRuleNames(List<String> ruleNames) {
        AST.ruleNames = ruleNames;
    }

    /**
     * Get payloads
     * @return payload for the node
     */
    public Object getPayload() {
        return payload;
    }

    /**
     * get the children
     * @return children of the node
     */
    List<AST> getChildren() {
        return new ArrayList<>(children);
    }

    /**
     * get the rule name
     * @param ruleIndex rule index
     * @return name corresponding to the index
     */
    private String getRuleName(int ruleIndex) {
        String ruleName;
        if (ruleIndex >= 0 && CollectionUtils.isNotEmpty(ruleNames) && ruleIndex < ruleNames.size()) {
            ruleName = ruleNames.get(ruleIndex);
        } else {
            ruleName = Integer.toString(ruleIndex);
        }
        return ruleName;
    }

    /**
     * get teh payload
     * @param tree input parse tree
     * @return the payload of this AST: a string in case it's an inner node (which
     * is the name of the parser rule), or a Token in case it is a leaf node.
     */
    private Object getPayload(ParseTree tree) {
        if (tree.getChildCount() == 0) {
            // A leaf node: return the tree's payload, which is a Token.
            return tree.getPayload();
        } else {
            int ruleIndex = ((ParserRuleContext) tree).getRuleIndex();
            return getRuleName(ruleIndex);
        }
    }

    /**
     * Fills this AST based on the parse tree.
     *
     * @param tree input parse tree for a node
     * @param ast  tree till now
     */
    private static void walk(ParseTree tree, AST ast) {
        if (tree.getChildCount() == 0) {
            // We've reached a leaf. We must create a new instance of an AST because
            // the constructor will make sure this new instance is added to its parent's
            // child nodes.
            new AST(ast, tree);
        } else if (tree.getChildCount() == 1) {
            // We've reached an inner node with a single child: we don't include this in
            // our AST.
            walk(tree.getChild(0), ast);
        } else if (tree.getChildCount() > 1) {
            for (int i = 0; i < tree.getChildCount(); i++) {
                AST temp = new AST(ast, tree.getChild(i));
                if (!(temp.payload instanceof Token)) {
                    // Only traverse down if the payload is not a Token.
                    walk(tree.getChild(i), temp);
                }
            }
        }
    }

    /**
     * Get the indent of the files
     * @param childListStack list of internal nodes
     * @return indentations for display
     */
    private String getIndent(List<List<AST>> childListStack) {
        StringBuilder indent = new StringBuilder();

        for (List<AST> childList : childListStack) {
            indent.append(!childList.isEmpty() ? "|  " : "   ");
        }
        return indent.toString();
    }

    /**
     * Convert the ASt into string format
     * @return string representation of the ast
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        AST ast = this;
        List<AST> firstStack = new ArrayList<>();
        firstStack.add(ast);

        List<List<AST>> childListStack = new ArrayList<>();
        childListStack.add(firstStack);

        while (!childListStack.isEmpty()) {
            List<AST> childStack = childListStack.get(childListStack.size() - 1);

            if (childStack.isEmpty()) {
                childListStack.remove(childListStack.size() - 1);
            } else {
                ast = childStack.remove(0);
                String caption;

                if (ast.payload instanceof Token) {
                    Token token = (Token) ast.payload;
                    caption = String.format("TOKEN[type: %s, text: %s, row: %d, col: %d]",
                            token.getType(),
                            token.getText().replace("\n", "\\n"),
                            token.getLine(),
                            token.getCharPositionInLine());
                } else {
                    caption = String.valueOf(ast.payload);
                }

                String indent = getIndent(childListStack);

                builder.append(indent)
                        .append(childStack.isEmpty() ? "'- " : "|- ")
                        .append(caption)
                        .append("\n");

                if (!ast.children.isEmpty()) {
                    List<AST> childrenList = new ArrayList<>(ast.children);
                    childListStack.add(childrenList);
                }
            }
        }

        return builder.toString();
    }

    /**
     * Get the has value of the ASt tree and node
     * @return hash for the AST node
     */
    public String getHash() {
        String caption;

        if (payload instanceof Token) {
            Token token = (Token) payload;
            if (token.getType() == CParser.Identifier ||
                    token.getType() == CParser.StringLiteral)
                caption = String.valueOf(token.getType());
            else
                caption = String.valueOf(token.getText().hashCode());
        } else {
            caption = String.valueOf(payload);
        }

        return caption;
    }
}