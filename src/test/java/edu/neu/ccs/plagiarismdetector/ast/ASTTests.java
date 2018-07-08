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

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Class for verifying ANTLR AST behavior
 */
public class ASTTests {

    /**
     * @param file     input file
     * @param encoding character setls
     * @return string representation of the file
     * @throws IOException exception thrown
     */
    private String readFile(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, encoding);
    }

    @Test
    public void printASTRulesTest() throws IOException {
        ASTService astService = new ASTService();
        String code = readFile(new File("src/test/resources/mock/student1/add.c"), Charset.forName("UTF-8"));
        String astRules = astService.printASTRules(code);
        assertNotNull("Rules should not be empty", astRules);
    }

    @Test
    public void createASTTest() throws IOException {
        ASTService astService = new ASTService();
        String code = readFile(new File("src/test/resources/mock/student1/add.c"), Charset.forName("UTF-8"));
        AST ast = astService.getAST(code);
        assertEquals("No. of immediate children?", 3, ast.getChildren().size());
        assertTrue("Representation is not empty", StringUtils.isNotEmpty(ast.toString()));
    }

    @Test
    public void checkPreOrderASTTest() throws IOException {
        ASTService astService = new ASTService();
        String code = readFile(new File("src/test/resources/mock/student1/BinaryDigit.c"),
                Charset.forName("UTF-8"));
        List<AST> nodes = astService.preOrder(code);
        assertEquals("Total no. of nodes", 21, nodes.size());
    }
}
