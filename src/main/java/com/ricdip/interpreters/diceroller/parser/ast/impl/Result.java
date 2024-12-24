package com.ricdip.interpreters.diceroller.parser.ast.impl;

import com.ricdip.interpreters.diceroller.parser.ast.Expression;
import com.ricdip.interpreters.diceroller.parser.ast.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class that identifies the root node of the AST.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Node {
    private Expression expression;

    @Override
    public String toString() {
        return expression.toString();
    }
}
