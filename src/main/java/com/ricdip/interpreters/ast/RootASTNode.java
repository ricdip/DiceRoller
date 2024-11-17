package com.ricdip.interpreters.ast;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
