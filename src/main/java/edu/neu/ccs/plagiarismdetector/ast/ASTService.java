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

import edu.neu.ccs.plagiarismdetector.ast.grammar.CLexer;
import edu.neu.ccs.plagiarismdetector.ast.grammar.CParser;
import edu.neu.ccs.plagiarismdetector.ast.listener.ASTRulePrinterListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service for all things AST
 */
@Service
public class ASTService {

    /**
     * Get the C parser to parse the C code file
     * @param code program text
     * @return parser implementation
     */
    private CParser getParser(String code) {
        CharStream charStream = CharStreams.fromString(code);
        CLexer lexer = new CLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return new CParser(tokens);
    }

    /**
     * Parse the AST node using the parser
     * @param parser C parser implementation
     * @return translation unit
     */
    private CParser.TranslationUnitContext parse(CParser parser) {
        return parser.translationUnit();
    }

    /**
     * @param code program text
     * @return AST rules corresponding to the codes
     */
    String printASTRules(String code) {
        CParser parser = getParser(code);
        ParseTree parseTree = parse(parser);
        ASTRulePrinterListener listener = new ASTRulePrinterListener(parser);

        ASTRuleWalker.DEFAULT.walk(listener, parseTree, 0);
        return listener.toString();
    }

    /**
     * Gee the ASt after parsing the source code
     * @param code program text
     * @return AST representation of the code
     */
    AST getAST(String code) {
        CParser parser = getParser(code);
        ParseTree parseTree = parse(parser);

        AST.setRuleNames(Arrays.asList(parser.getRuleNames()));
        return new AST(parseTree);
    }

    /**
     * Pre order traversal of the AST
     * @param node AST to be pre ordered
     */
    private List<AST> preOrder(AST node) {
        List<AST> nodes = new ArrayList<>();
        if (node == null)
            return nodes;
        nodes.add(node);
        for (AST child : node.getChildren()) {
            nodes.addAll(preOrder(child));
        }
        return nodes;
    }

    /**
     * @param code program text
     * @return preordered list of nodes
     */
    public List<AST> preOrder(String code) {
        AST ast = getAST(code);
        return preOrder(ast);
    }

}
