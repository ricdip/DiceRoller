package com.ricdip.interpreters.ast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that identifies the root node of the AST.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RootASTNode implements ASTNode {
    private Expression expression;

    @Override
    public String toString() {
        return String.format("%s", expression.toString());
    }
}
